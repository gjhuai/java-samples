package redis.ex1

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

class Publisher(private val jedisPool: JedisPool, private val channel: String) {

    fun start() {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val jedis = jedisPool.resource
        while (true) {
            try {
                val line = reader.readLine()
                if ("quit" != line) {
                    jedis.publish(channel, line)
                } else {
                    break
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        println("publisher exit!")
    }
}