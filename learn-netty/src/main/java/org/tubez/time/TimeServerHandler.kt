package org.tubez.time

import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext

/**
 * Created by gjh on 2016/8/26.
 */
class TimeServerHandler {
    fun channelActive(ctx: ChannelHandlerContext) { // (1) channelActive()方法将会在连接被建立并且准备进行通信时被调用。
        val time = ctx.alloc().buffer(4) // (2)
        time.writeInt((System.currentTimeMillis() / 1000L + 2208988800L).toInt())

        val f = ctx.writeAndFlush(time) // (3)
        f.addListener(object : ChannelFutureListener {
            override fun operationComplete(future: ChannelFuture) {
                assert(f === future)
                ctx.close()
            }
        }) // (4)
    }

    fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}