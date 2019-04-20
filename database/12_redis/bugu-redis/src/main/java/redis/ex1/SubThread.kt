package redis.ex1

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool


class SubThread(private val jedisPool: JedisPool, private val channel: String) : Thread("SubThread") {
    private val subscriber = Subscriber()

    override fun run() {
        println("subscribe redis, channel $channel, thread will be blocked")
        var jedis: Jedis? = null
        try {
            jedis = jedisPool.resource
            jedis!!.subscribe(subscriber, channel)
        } catch (e: Exception) {
            println("subsrcibe channel error, $e")
        } finally {
            if (jedis != null) {
                jedis.close()
            }
        }
    }
}