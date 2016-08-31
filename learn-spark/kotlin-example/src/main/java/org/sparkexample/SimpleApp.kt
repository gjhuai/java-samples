package org.sparkexample

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

/**
 * Created by gjh on 2016/8/31.
 */
object SimpleApp {
    @JvmStatic fun main(args: Array<String>) {
        val logFile = "pom.xml"
        val conf = SparkConf().setAppName("Simple Application").setMaster("local")
        val sc = JavaSparkContext(conf)
        val logData = sc.textFile(logFile).cache()
        val numAs = logData.filter{line -> line.contains("a")}.count()
        val numBs = logData.filter{line -> line.contains("b")}.count()
        println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
    }
}