package org.tubez.discard.server

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext

/**
 * Created by gjh on 2016/8/26.
 */
class DiscardServerHandler : ChannelHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) { // (2)
        // Discard the received data silently.
        (msg as ByteBuf).release()  // (3)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace()
        ctx.close()
    }
}