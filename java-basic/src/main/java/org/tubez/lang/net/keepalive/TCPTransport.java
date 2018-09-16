package org.tubez.lang.net.keepalive;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class TCPTransport {

    private static final int SERVER_PORT=1234;
 
    private static   Hashtable<String, Socket> ht = new Hashtable();
    
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        System.out.println("VNTCenter.main()===TCP SERVER==============");
        ServerSocket vntServer = null;
        try{
            vntServer= new ServerSocket(SERVER_PORT);

            System.out.println("Listening Port is "+vntServer.getLocalPort()+"...");
            while(true){
                Socket vntClient=vntServer.accept();
                System.out.println("have a client entering , the IP is "+vntClient.getInetAddress()+"...");
                System.out.println("have a client entering , the Port is "+vntClient.getPort()+"...");
                new GetGpsThreadFun(vntClient).start();
                ht.put(vntClient.getInetAddress().toString(),vntClient);
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            vntServer.close();
        }
    }
}

class GetGpsThreadFun extends Thread{
    private Socket vntThreadClient;

    public GetGpsThreadFun(Socket vntThreadSocket){
        vntThreadClient=vntThreadSocket;
    }

    public void run(){
        try{
            OutputStream out = vntThreadClient.getOutputStream();
            int i = 0;
            while (i < 10) {
                out.write(("test" + i).getBytes());  // 发送
                out.flush();
                Thread.sleep(1000);
                i++;
            }

//        	byte[] data = new byte[128];
//            while(true){
//            	int len = vntThreadClient.getInputStream().read(data);
//            	if (len > 0){
//            		System.out.println(vntThreadClient.getInetAddress()+":"+vntThreadClient.getPort()+":"+new String(data,0,len));
//                  if("shutdown".equals(vntThreadClient)){
//                      break;
//                  }
//            	}
//            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}