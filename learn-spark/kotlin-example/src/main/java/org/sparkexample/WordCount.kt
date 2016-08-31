package org.sparkexample

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.FlatMapFunction
import org.apache.spark.api.java.function.Function2
import org.apache.spark.api.java.function.PairFunction
import scala.Tuple2

object WordCount {
    @JvmStatic fun main(args: Array<String>) {
        if (args.size < 1) {
            System.err.println("Please provide the input file full path as argument")
            System.exit(0)
        }

        val conf = SparkConf().setAppName("org.sparkexample.WordCount").setMaster("local")
        val context = JavaSparkContext(conf)

        val file = context.textFile(args[0])

        val words = file.flatMap{s -> s.split(" ")}
        val pairs = words.mapToPair{s -> Tuple2<String, Int>(s, 1)}
        val counter = pairs.reduceByKey{ a, b -> a + b }

        counter.saveAsTextFile(args[1])
        context.close()
    }
}

