package org.tubez.echo.server

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.*
import io.netty.util.CharsetUtil

/**
 * Created by gjh on 2016/8/26.
 */

@ChannelHandler.Sharable // 标识这类的实例之间可以在 channel 里面共享
class EchoServerHandler : ChannelHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any){
        val inBuf = msg as ByteBuf
        println("Server received: " + inBuf.toString(CharsetUtil.UTF_8))
        // 将所接收的消息返回给发送者。注意，这还没有冲刷数据
        ctx.write(inBuf)
    }

    @Throws(Exception::class)
    override fun channelReadComplete(ctx: ChannelHandlerContext){
        // 冲刷所有待审消息到远程节点。关闭通道后，操作完成
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener { io.netty.channel.ChannelFutureListener.CLOSE }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable){
        cause.printStackTrace()     // 打印异常堆栈跟踪
        ctx.close()     // 关闭通道
    }
}