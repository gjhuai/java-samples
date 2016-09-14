package redis.ex1

import redis.clients.jedis.JedisPubSub


class Subscriber : JedisPubSub() {

    override fun onMessage(channel: String, message: String?) {
        println("receive: channel $channel, message $message")
    }

    override fun onSubscribe(channel: String, subscribedChannels: Int) {
        println("subscribe success, channel $channel, subscribedChannels $subscribedChannels")
    }

    override fun onUnsubscribe(channel: String, subscribedChannels: Int) {
        println("unsubscribe, channel $channel, subscribedChannels $subscribedChannels")
    }
}