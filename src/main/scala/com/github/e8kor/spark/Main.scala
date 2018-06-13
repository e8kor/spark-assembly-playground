package com.github.e8kor.spark

import com.typesafe.scalalogging.LazyLogging


object Main extends SparkSessionWrapper with LazyLogging {


  def main(args: Array[String]): Unit = {
    logger.info("reading file: " + "src/main/resources/sales.csv")
    val df = spark.read.option("header","true").csv("src/main/resources/sales.csv")

    df.show()
  }
}
