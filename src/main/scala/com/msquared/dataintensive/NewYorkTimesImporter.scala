package com.msquared.dataintensive

import java.sql.Date
import java.text.SimpleDateFormat

import com.datastax.driver.core.{Cluster, Session}
import com.datastax.spark.connector._
import com.datastax.spark.connector.AllColumns
import com.msquared.dataintensive.model.NYTArticleRow
import org.apache.spark.sql.SparkSession
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.read

object NewYorkTimesImporter {
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
    import spark.implicits._
    session.execute("USE stock;")

    // ##### NYT Table Initialization
    session.execute(
      """
        |CREATE TABLE IF NOT EXISTS nyt (web_url text primary key, snippet text, lead_paragraph text, abstra text, print_page text, source text, pub_date date, document_type text, news_desk text, section_name text, subsection_name text, type_of_material text, word_count text);
      """.stripMargin)

    // This is a path to the file containing nyt articles data
    val dataFilePath = "file:///C:/Projects/historical_trend/nyt-dump3/part-00000"

    val rdd = spark.sparkContext.textFile(dataFilePath)
      .map(line => line.substring(1, line.length - 1).split(",", 2))
      .map(arr => {


        case object DateSerializer extends CustomSerializer[java.sql.Date](format => ( {
          case JString(s) => Date.valueOf(s.substring(0, 10))
          case JNull => null
        }, {
          case d: Date => JString(d.toString)
        }))

        implicit val formats: Formats = DefaultFormats + DateSerializer
        val json = arr(1).replace("\"abstract\"", "\"abstra\"")
        val jsonObject = parse(json)
        val article = read[NYTArticleRow](json)
        article
      }).saveToCassandra("stock", "nyt", AllColumns)
  }
}
