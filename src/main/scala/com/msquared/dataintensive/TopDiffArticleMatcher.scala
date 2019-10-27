package com.msquared.dataintensive

import com.datastax.driver.core.{Cluster, Session}
import org.apache.spark.sql.SparkSession

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
  }
}
