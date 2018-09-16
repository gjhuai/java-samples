package org.tubez.lang.nio.socket.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 实现TCP/IP+NIO 方式的系统间通讯的代码，客户端：
 * 
 * @author chris.guan
 */
public class ClientTcpNio {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9527;
        
        Selector selector = Selector.open();
        
        SocketChannel clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);
        clientChannel.connect(new InetSocketAddress(host, port));
        
        clientChannel.register(selector, SelectionKey.OP_CONNECT);
        
        while (true) {
            if (clientChannel.isConnected()) {
            	request(selector, clientChannel);	// 请求服务器
            }

            int nKeys = selector.select(3 * 1000);
            if (nKeys <= 0 ) {	// 等待超时处理
                System.err.println("handle select timeout Exception");
                clientChannel.close();
                return;
            }
            
            for (SelectionKey selectionKey : selector.selectedKeys()) {
                if (selectionKey.isConnectable()) {
                    connect(selector, selectionKey);	// 连接服务器
                } else if (selectionKey.isReadable()) {
                    handleResponse(selectionKey);	// 处理响应
                }
            }
            selector.selectedKeys().clear();
        }
    }


	private static void connect(Selector selector, SelectionKey selectionKey)
			throws IOException, ClosedChannelException {
		SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		socketChannel.finishConnect();
	}

	private static void request(Selector selector, SocketChannel clientChannel)
			throws IOException {
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		String command = systemIn.readLine();
		clientChannel.write(Charset.forName("UTF-8").encode(command));
		
		if (command == null || "quit".equalsIgnoreCase(command)) {
		    System.out.println("Client quit!");
		    
		    systemIn.close();
		    clientChannel.close();
		    selector.close();
		    System.exit(0);
		}
	}
	
	private static void handleResponse(SelectionKey selectionKey)
			throws IOException {
		SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
		StringBuilder sb = new StringBuilder();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try {
		    int readBytes = 0;
		    int ret = 0;
		    while ((ret = socketChannel.read(byteBuffer)) > 0) {
		        readBytes += ret;
		        byteBuffer.flip();
		        
		        sb.append(Charset.forName("UTF-8").decode(byteBuffer).toString());
		        
		        byteBuffer.clear();
		    }
		    
		    if (readBytes == 0) {
		        System.err.println("handle opposite close Exception");
		        socketChannel.close();
		    }
		} catch (IOException e) {
		    System.err.println("handle read Exception");
		    socketChannel.close();
		} finally {
		    byteBuffer.clear();
		}
		
		String message = sb.toString();
		System.out.println(message);
	}

}