package redis.ex1

import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig


object PubSubDemo {
    var channel = "mychannel"

    @JvmStatic fun main(args: Array<String>) {
        // 替换成你的reids地址和端口
        val redisIp = "127.0.0.1"
        val reidsPort = 6379
        val jedisPool = JedisPool(JedisPoolConfig(), redisIp, reidsPort)
        println("redis pool is starting, redis ip $redisIp, redis port $reidsPort")

        val subThread = SubThread(jedisPool, channel)
        subThread.start()

        val publisher = Publisher(jedisPool, channel)
        publisher.start()
    }


}