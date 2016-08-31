package org.sparkexample.streaming


import org.apache.spark.SparkConf
import org.apache.spark.api.java.StorageLevels
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.api.java.JavaStreamingContext
import scala.Tuple2

/**
 * Counts words in UTF8 encoded, '\n' delimited text received from the network every second.

 * Usage: NetworkWordCount
 *  and  describe the TCP server that Spark Streaming would connect to receive data.

 * To run this on your local machine, you need to first run a Netcat server
 * `$ nc -lk 9999`
 * and then run the example
 * `$ bin/run-example org.apache.spark.examples.streaming.NetworkWordCount localhost 9999`
 */
object NetworkWordCount {

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
//        if (args.size < 2) {
//            System.err.println("Usage: NetworkWordCount <hostname> <port>")
//            System.exit(1)
//        }

        val host: String = "192.168.0.204"
        val port: Int = 9999

        StreamingExamples.setStreamingLogLevels();

        // Create the context with a 1 second batch size
        val sparkConf = SparkConf().setAppName("NetworkWordCount").setMaster("local[2]")
        val ssc = JavaStreamingContext(sparkConf, Durations.seconds(1))

        // Create a JavaReceiverInputDStream on target ip:port and count the
        // words in input stream of \n delimited text (eg. generated by 'nc')
        // Note that no duplication in storage level only for running locally.
        // Replication necessary in distributed scenario for fault tolerance.
        val lines = ssc.socketTextStream(host, port, StorageLevels.MEMORY_AND_DISK_SER)
        val words = lines.flatMap{ it.split(" ") }
        val wordCounts = words.mapToPair { w -> Tuple2(w, 1)
            }.reduceByKey { i1, i2 -> i1 + i2 }

        wordCounts.print()

        ssc.start()
        ssc.awaitTermination()
    }
}