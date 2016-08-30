package org.tubez.echo.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.InetSocketAddress

class EchoServer(private val port: Int){

    @Throws(Exception::class)
    fun start(){
        val group = NioEventLoopGroup()
        try{
            val b = ServerBootstrap()
            b.group(group)
                .channel(NioServerSocketChannel::class.java)    // 指定使用 NIO 的传输 Channel
                .localAddress(InetSocketAddress(port))
                .childHandler(object : ChannelInitializer<SocketChannel>() {    // 添加 EchoServerHandler 到 ChannelPipeline
                    @Throws(Exception::class)
                    override fun initChannel(ch: SocketChannel){
                        ch.pipeline().addLast(EchoServerHandler())
                    }
                })

            val f = b.bind().sync()     // 绑定的服务器;sync 等待服务器关闭
            println(EchoServer::class.java.name + " started and listen on " + f.channel().localAddress())
            f.channel().closeFuture().sync()        // 关闭 channel 和 块，直到它被关闭
        } finally {
            group.shutdownGracefully().sync()       // 关机的 EventLoopGroup，释放所有资源
        }
    }

    companion object{
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>){
            if (args.size!=1){
                System.err.println("Usage: \${EchoServer::class.java.simpleName} <port>")
                return
            }
            val port = Integer.valueOf(args[0])
            EchoServer(port).start()

        }
    }
}
