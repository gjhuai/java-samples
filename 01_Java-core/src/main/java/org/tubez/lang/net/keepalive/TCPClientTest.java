package org.tubez.lang.net.keepalive;

// https://my.oschina.net/actiontime/blog/874774

import java.io.OutputStream;
import java.net.Socket;

public class TCPClientTest {

	private static final int SERVER_PORT=1234;

    public static void main(String[] args) throws Exception {
		   Socket socket = new Socket("127.0.0.1", SERVER_PORT);
		   System.out.println("Connected to server...sending command string");

			byte[] data = new byte[128];
            while(true){
            	int len = socket.getInputStream().read(data);
            	if (len > 0){
            		System.out.println(socket.getInetAddress()+":"+socket.getPort()+":"+new String(data,0,len));
                  if("shutdown".equals(socket)){
                      break;
                  }
            	}
            }

//		   OutputStream out = socket.getOutputStream();
//		   int i = 0;
//		   while (i < 10) {
//		   out.write(("test" + i).getBytes());  // 发送
//		   	out.flush();
//		   	Thread.sleep(1000);
//		   	i++;
//		   }
//		   out.close();
		   socket.close();
	}
}