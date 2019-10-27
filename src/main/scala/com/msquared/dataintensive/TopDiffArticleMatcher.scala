package com.msquared.dataintensive

import com.datastax.driver.core.{Cluster, Session}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object TopDiffArticleMatcher {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession.builder().appName("Historical Trend")
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .master("local[2]")
      .getOrCreate()

    // Cassandra config
    val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
    implicit val session: Session = cluster.connect()

    session.execute("USE stock;")

    joinDiffWithArticles("dow30_diff")
    joinDiffWithArticles("nasdaq_diff")
    joinDiffWithArticles("historical_prices_diff")
    joinDiffWithArticles("sp500_diff")
  }

  private def joinDiffWithArticles(diffTableName: String)(implicit spark: SparkSession): Unit = {
    val diffDF = spark.read.format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> s"$diffTableName", "keyspace" -> "stock")).load()

    val nyt = spark.read.format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "nyt", "keyspace" -> "stock")).load()

    import spark.implicits._
    diffDF.orderBy("difference_close").limit(30)
      .join(nyt.filter($"news_desk" === "Business"), $"date" === $"pub_date")
      .select("date", "difference_close", "difference_open", "web_url", "snippet", "lead_paragraph")
      .sort("difference_close")
      .coalesce(1)
      .write
      .format("json")
      .save(diffTableName + "_nyt_diff_close_asc")

    diffDF.orderBy(desc("difference_close")).limit(30)
      .join(nyt.filter($"news_desk" === "Business"), $"date" === $"pub_date")
      .select("date", "difference_close", "difference_open", "web_url", "snippet")
      .sort(desc("difference_close"))
      .coalesce(1)
      .write
      .format("json")
      .save(diffTableName + "_nyt_diff_close_desc")
  }
}
