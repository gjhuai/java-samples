package org.sparkexample.streaming

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.Logging

/**
 * Created by gjh on 2016/8/31.
 */
abstract class StreamingExamples : Logging {
    companion object{

    /** Set reasonable logging levels for streaming if the user has not configured log4j. */
    @JvmStatic fun setStreamingLogLevels() {
        val log4jInitialized = Logger.getRootLogger().getAllAppenders().hasMoreElements()
        if (!log4jInitialized) {
            // We first log something to initialize Spark's default logging, then we override the
            // logging level.
//            logInfo("Setting log level to [WARN] for streaming example." +
//                    " To override add a custom log4j.properties to the classpath.")
            Logger.getRootLogger().setLevel(Level.INFO)
        }
    }
    }
}