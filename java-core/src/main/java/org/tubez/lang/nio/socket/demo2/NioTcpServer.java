package org.tubez.lang.nio.socket.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
  
/** 
 * NIO服务端 
 * @author 小路 
 */  
public class NioTcpServer {  
    //通道管理器  
    private Selector selector;  
  
    /** 
     * 获得一个ServerSocket通道，并对该通道做一些初始化的工作 
     * @param port  绑定的端口号 
     * @throws IOException 
     */  
    public void initServer(int port) throws IOException {  
        // 获得一个ServerSocket通道  
        ServerSocketChannel serverChannel = ServerSocketChannel.open();  
        // 设置通道为非阻塞  
        serverChannel.configureBlocking(false);  
        // 将该通道对应的ServerSocket绑定到port端口  
        serverChannel.socket().bind(new InetSocketAddress(port));  
        // 获得一个通道管理器  
        this.selector = Selector.open();  
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，  
        //当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。  
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);  
    }  
  
    /** 
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理 
     * @throws IOException 
     */  
    public void listen() throws IOException {  
        System.out.println("服务端启动成功！");  
        // 轮询访问selector  
        while (true) {  
            //当注册的事件到达时，方法返回；否则,该方法会一直阻塞  
            selector.select();

            // 获得selector中选中的项的迭代器，选中的项为注册的事件  
            for (SelectionKey key : selector.selectedKeys()) {
                // 客户端请求连接事件  
                if (key.isAcceptable()) {
                	//System.out.println("----> 接受客户端连接请求");
                    accept(key);
                    
                } else if (key.isReadable()) {  // 获得了可读的事件  
                    read(key);
                }  
            }
            
            selector.selectedKeys().clear();	// 清楚已选的key, 以防重复处理
        }  
    }

	private void accept(SelectionKey key) throws IOException,
			ClosedChannelException {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();  
		// 获得和客户端连接的通道  
		SocketChannel channel = server.accept(); 
		// 设置成非阻塞  
		channel.configureBlocking(false);  
		
        System.out.println("Accept client : ");

		//在这里可以给客户端发送信息哦  
		channel.write(ByteBuffer.wrap(new String("Hello, Guy").getBytes()));  
		//在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。  
		channel.register(this.selector, SelectionKey.OP_READ);
	}  
    /** 
     * 处理读取客户端发来的信息 的事件 
     * @param key 
     * @throws IOException  
     */  
    public void read(SelectionKey key) throws IOException{  
        // 服务器可读取消息:得到事件发生的Socket通道  
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(30);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data,"UTF-8").trim();
        System.out.println("    from client : "+msg);
        ByteBuffer outBuffer = ByteBuffer.wrap("你好，Client".getBytes("UTF-8"));  
        channel.write(outBuffer);// 将消息回送给客户端
    }
      
    /** 
     * 启动服务端测试 
     * @throws IOException  
     */  
    public static void main(String[] args) throws IOException {  
        NioTcpServer server = new NioTcpServer();  
        server.initServer(8000);
        server.listen();
    }  
  
}  