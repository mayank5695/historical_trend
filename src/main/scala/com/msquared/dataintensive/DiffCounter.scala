package com.msquared.dataintensive

import com.datastax.driver.core.{Cluster, Session}
import com.datastax.spark.connector._
import com.msquared.dataintensive.model.StockRow
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

object DiffCounter {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession.builder().appName("Historical Trend")
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .master("local[2]")
      .getOrCreate()

    // Cassandra config
    val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
    implicit val session: Session = cluster.connect()

    session.execute("USE stock;")

    computeDiffTable("dow30", "dow30_diff")
    computeDiffTable("historical_prices", "historical_prices_diff")
    computeDiffTable("nasdaq", "nasdaq_diff")
    computeDiffTable("sp500", "sp500_diff")
  }

  def computeDiffTable(fromTableName: String, toTableName: String)(implicit session: Session, spark: SparkSession): Unit = {
    session.execute(
      s"""
         |CREATE TABLE IF NOT EXISTS $toTableName (date date primary key, high float, low float, open float, close float, adj_close float, volume float, difference_close float, difference_open float);
      """.stripMargin)

    import spark.implicits._
    val stockRdd = spark.sparkContext.cassandraTable[StockRow]("stock", fromTableName)

    val w = Window.partitionBy().orderBy("date")
    val dfDiffClose = stockRdd.toDF().withColumn("difference_close", $"close" - when(
      lag("close", 1).over(w).isNull, 0)
      .otherwise(lag("close", 1).over(w)))
    val dfDiffOpen = dfDiffClose.withColumn("difference_open", $"open" - when(
      lag("open", 1).over(w).isNull, 0)
      .otherwise(lag("open", 1).over(w)))
    dfDiffOpen.write.format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> s"$toTableName", "keyspace" -> "stock"))
      .save()
  }
}
