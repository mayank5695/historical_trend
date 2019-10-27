package com.msquared.dataintensive

import java.time.LocalDate
import java.time.temporal.ChronoUnit

import com.datastax.driver.core.{Cluster, Session}
import org.apache.spark.sql.SparkSession
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scalaj.http.Http


object NewYorkTimesDownloader {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Historical Trend")
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .master("local[2]")
      .getOrCreate()

    // Cassandra config
    val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
    val session = cluster.connect()
    initializeNewYorkTimeTable(spark, session)
  }

  private def initializeNewYorkTimeTable(spark: SparkSession, session: Session): Unit = {
    // This variable sets up the number of months back for which you want to download articles.
    val MONTHS_BACK_NUM = 240

    session.execute("CREATE KEYSPACE IF NOT EXISTS stock WITH REPLICATION = " +
      "{ 'class' : 'SimpleStrategy', 'replication_factor' : 1 };")
    session.execute("USE stock;")

    val startingDate = LocalDate.now().minusMonths(MONTHS_BACK_NUM)
    val count = startingDate.until(LocalDate.now(), ChronoUnit.MONTHS)
    val urls = spark.sparkContext.parallelize((0 until count.toInt).map(startingDate.plusMonths(_))).map(date => {
      "https://api.nytimes.com/svc/archive/v1/" + date.getYear.toString + "/" + date.getMonthValue.toString + ".json?api-key=yLlytjZxWghSgkePdQbQhTGWdNCk5JOB"
    })
    val urlcount = urls.count()
    println("urls count: " + urlcount)

    val responses = urls.map(url => {
      var response = Http(url).asString.body
      while (response.contains("faultstring")) {
        Thread.sleep(10000)
        response = Http(url).asString.body
      }
      val noMultimediaResponse = parse(response) transform {
        case x => x transform {
          case y => y transform {
            case JArray(objs) => {
              JArray(objs.map(_ transformField {
                case ("multimedia", _) => ("multimedia", JNothing)
                case ("byline", _) => ("byline", JNothing)
                case ("keywords", _) => ("keywords", JNothing)
                case ("headline", _) => ("headline", JNothing)
                case ("_id", _) => ("_id", JNothing)
              }))
            }
          }
        }
      }
      noMultimediaResponse
    }).flatMap(response => {
      (response \\ "docs").asInstanceOf[JArray].children.map(child => (compact(render(child \ "web_url")), compact(render(child))))
    }).coalesce(1).saveAsTextFile("nyt-dump3")
  }
}
