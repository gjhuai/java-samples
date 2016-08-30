package org.tubez.echo.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress

/**
 * Created by gjh on 2016/8/26.
 */
class EchoClient(private val host: String, private val port: Int) {

    @Throws(Exception::class)
    fun start() {
        val group = NioEventLoopGroup();
        try {
            val bs = Bootstrap()                //1
            bs.group(group)                                //2
                .channel(NioSocketChannel::class.java)            //3
                .remoteAddress(InetSocketAddress(host, port))
                    .handler(object: ChannelInitializer<SocketChannel>() {    //5
                        @Throws(Exception::class)
                        override fun initChannel(ch: SocketChannel){
                            ch.pipeline().addLast(EchoClientHandler());
                        }
                    });

            val f = bs.connect().sync();        //6
            f.channel().closeFuture().sync();            //7
        } finally {
            group.shutdownGracefully().sync();            //8
        }
    }

    companion object{
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            if (args.size != 2) {
                System.err.println(
                        "Usage: \${EchoClient::class.simpleName} <host> <port>");
                return;
            }

        val host = args[0];
        val port = Integer.parseInt(args[1]);

        EchoClient(host, port).start();
    }
    }
}