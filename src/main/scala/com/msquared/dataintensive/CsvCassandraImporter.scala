package com.msquared.dataintensive

import com.datastax.driver.core.{Cluster, Session}
import com.datastax.spark.connector._
import com.msquared.dataintensive.model.StockRow
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.to_date


object CsvCassandraImporter {
  // CHANGE IT BEFORE RUNNING TO YOUR OWN PATH
  val projectPath = "C:/Projects/historical_trend"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Historical Trend")
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .master("local[2]")
      .getOrCreate()

    // Cassandra config
    val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
    val session = cluster.connect()
    initializeCsvTables(spark, session)
  }

  private def initializeCsvTables(spark: SparkSession, session: Session): Unit = {
    import spark.implicits._
    session.execute("CREATE KEYSPACE IF NOT EXISTS stock WITH REPLICATION = " +
      "{ 'class' : 'SimpleStrategy', 'replication_factor' : 1 };")
    session.execute("USE stock;")

    // ##### Dow30 Table Initialization
    session.execute(
      """
        |CREATE TABLE IF NOT EXISTS dow30 (date date primary key, high float, low float, open float, close float, adj_close float, volume float);
      """.stripMargin)
    val dow30Count = spark.sparkContext.cassandraTable[StockRow]("stock", "dow30").count()
    println(dow30Count)
    if (dow30Count == 0) {
      val df = spark.read.format("csv").option("header", value = true).option("ignoreLeadingWhiteSpace", value = true)
        .load("file:///" + projectPath + "/data/Dow30.csv")
      val df2 = df.withColumn("date", to_date($"date", "yyyy-mm-dd"))
      df2.write.format("org.apache.spark.sql.cassandra")
        .options(Map("table" -> "dow30", "keyspace" -> "stock"))
        .save()
    }

    // ##### Historical Prices Table Initialization
    session.execute(
      """
        |CREATE TABLE IF NOT EXISTS historical_prices (date date primary key, high float, low float, open float, close float, adj_close float, volume float);
      """.stripMargin)
    val historicalPricesCount = spark.sparkContext.cassandraTable[StockRow]("stock", "historical_prices").count()
    println(historicalPricesCount)
    if (historicalPricesCount == 0) {
      val df = spark.read.format("csv").option("header", value = true).option("ignoreLeadingWhiteSpace", value = true)
        .load("file:///" + projectPath + "/data/HistoricalPrices.csv")
      val df2 = df.withColumn("date", to_date($"date", "MM/dd/yy"))
      df2.write.format("org.apache.spark.sql.cassandra")
        .options(Map("table" -> "historical_prices", "keyspace" -> "stock"))
        .save()
    }

    // ##### Nasdaq Table Initialization
    session.execute(
      """
        |CREATE TABLE IF NOT EXISTS nasdaq (date date primary key, high float, low float, open float, close float, adj_close float, volume float);
      """.stripMargin)
    val nasdaqCount = spark.sparkContext.cassandraTable[StockRow]("stock", "nasdaq").count()
    println(nasdaqCount)
    if (nasdaqCount == 0) {
      val df = spark.read.format("csv").option("header", value = true).option("ignoreLeadingWhiteSpace", value = true)
        .load("file:///" + projectPath + "/data/Nasdaq.csv")
      val df2 = df.withColumn("date", to_date($"date", "yyyy-mm-dd"))
      df2.write.format("org.apache.spark.sql.cassandra")
        .options(Map("table" -> "nasdaq", "keyspace" -> "stock"))
        .save()
    }

    // ##### sp500 Table Initialization
    session.execute(
      """
        |CREATE TABLE IF NOT EXISTS sp500 (date date primary key, high float, low float, open float, close float, adj_close float, volume float);
      """.stripMargin)
    val sp500count = spark.sparkContext.cassandraTable[StockRow]("stock", "sp500").count()
    println(sp500count)
    if (sp500count == 0) {
      val df = spark.read.format("csv").option("header", value = true).option("ignoreLeadingWhiteSpace", value = true)
        .load("file:///" + projectPath + "/data/S&P500.csv")
      val df2 = df.withColumn("date", to_date($"date", "yyyy-mm-dd"))
      df2.write.format("org.apache.spark.sql.cassandra")
        .options(Map("table" -> "sp500", "keyspace" -> "stock"))
        .save()
    }
  }
}
