package org.tubez.echo.client

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.CharsetUtil

/**
 * Created by gjh on 2016/8/26.
 */
@ChannelHandler.Sharable
class EchoClientHandler() : SimpleChannelInboundHandler<ByteBuf>(){
    override fun channelActive(ctx: ChannelHandlerContext){
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", //2
                CharsetUtil.UTF_8));
    }

    override fun messageReceived(ctx: ChannelHandlerContext, buf: ByteBuf){
        println("Client received: " + buf.toString(CharsetUtil.UTF_8))
    }

}