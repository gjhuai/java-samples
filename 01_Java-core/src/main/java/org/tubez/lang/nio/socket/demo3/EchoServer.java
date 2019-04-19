/**
 *    基于NIO的TCP连接的建立步骤

  服务端
    1、传建一个Selector实例；

    2、将其注册到各种信道，并指定每个信道上感兴趣的I/O操作；

    3、重复执行：

        1）调用一种select（）方法；

        2）获取选取的键列表；

        3）对于已选键集中的每个键：

           a、获取信道，并从键中获取附件（如果为信道及其相关的key添加了附件的话）；

           b、确定准备就绪的操纵并执行，如果是accept操作，将接收的信道设置为非阻塞模式，并注册到选择器；

           c、如果需要，修改键的兴趣操作集；

           d、从已选键集中移除键
           
           
     客户端

   与基于多线程的TCP客户端大致相同，只是这里是通过信道建立的连接，但在等待连接建立及读写时，我们可以异步地执行其他任务。
 */
package org.tubez.lang.nio.socket.demo3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EchoServer {

    private ServerSocketChannel server = ServerSocketChannel.open();
    private Selector selector = Selector.open();
    private SelectionKey selection;
    private Map<Channel, String> dataMap = new HashMap<Channel, String>();

    private EchoServer() throws IOException {
        logln("Starting server");
        server.configureBlocking(false);
        server.socket().bind(new InetSocketAddress(4444));
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void start() throws IOException {
        logln("Server started");
        while (true) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                selection = keyIterator.next();
                keyIterator.remove();
                if (!selection.isValid()) {
                    invalid();
                } else if (selection.isAcceptable()) {
                    createClient();
                } else if (selection.isReadable()) {
                    read();
                } else if (selection.isWritable()) {
                    write();
                }
            }
        }
    }

    private void invalid() throws IOException {
        logln("Invalid selection");
        selection.channel().close();
        selection.cancel();
    }

    private void createClient() throws IOException {
        SocketChannel client = server.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void read() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel client = (SocketChannel) selection.channel();
        int readed = client.read(buffer);
        StringBuilder sb = new StringBuilder();
        if (readed == -1) {
            logln("Connection closed by client");
            client.close();
            selection.cancel();
            return;
        } else {
            buffer.flip();
            sb.append(new String(buffer.array(), "UTF-8").trim());
            buffer.clear();
        }
        readed = client.read(buffer);
        while (readed != -1 && readed != 0) {
            buffer.flip();
            sb.append(new String(buffer.array(), "UTF-8").trim());
            buffer.clear();
            readed = client.read(buffer);
        }
        logln("Incoming:");
        log(sb.toString());
        logln("");
        dataMap.put(client, sb.toString());
        selection.interestOps(SelectionKey.OP_WRITE);
    }

    private void write() throws IOException {
        SocketChannel client = (SocketChannel) selection.channel();
        String data = dataMap.get(client);
        logln("Outcomming:");
        log(data);
        logln("");
        client.write(ByteBuffer.wrap(data.getBytes()));
        selection.interestOps(SelectionKey.OP_READ);
    }

    private static void logln(String s) {
        System.out.println(s);
    }

    private static void log(String s) {
        System.out.print(s);
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().start();
    }
}