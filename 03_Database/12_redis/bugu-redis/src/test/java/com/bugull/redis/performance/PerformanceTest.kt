/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bugull.redis.performance

import com.bugull.redis.RedisConnection
import com.bugull.redis.listener.TopicListener
import com.bugull.redis.mq.MQClient
import java.util.concurrent.atomic.AtomicLong
import org.junit.Test

/**

 * @author frankwen
 */
class PerformanceTest {

    internal var count = AtomicLong(0)

    internal var beginTime: Long = 0
    internal var endTime: Long = 0

    @Test
    @Throws(Exception::class)
    fun test() {
        val conn = RedisConnection.getInstance()
//        conn.setHost("127.0.0.1")
        conn.setHost("192.168.0.204")
        //        conn.setPassword("foobared");
        conn.connect()

        val client = conn.mqClient

        testSubscribe(client)

        Thread.sleep(1000)

        testPublish(client)

        Thread.sleep(10L * 1000L)

        conn.disconnect()
    }


    @Throws(Exception::class)
    fun testPublish(client: MQClient) {

        for (i in 0..9) {
            val task = Runnable {
                try {
                    for (i in 0..4999) {
                        client.publish("my_topic", "hello".toByteArray())
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            Thread(task).start()
        }
    }


    @Throws(Exception::class)
    fun testSubscribe(client: MQClient) {

        val listener = object : TopicListener() {
            override fun onTopicMessage(topic: String, message: ByteArray) {
                val value = count.incrementAndGet()
                if (value == 1L) {
                    beginTime = System.currentTimeMillis()
                } else if (value == 50000L) {
                    endTime = System.currentTimeMillis()
                    println("use " + (endTime - beginTime) + "ms to receive 10000 messages.")
                }
            }
        }

        client.setTopicListener(listener)

        client.subscribe("my_topic")

        println("Subscribe success! Listening message...")
    }

}
