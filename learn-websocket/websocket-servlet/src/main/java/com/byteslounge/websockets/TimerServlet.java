package com.byteslounge.websockets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/timer")
public class TimerServlet {
	
	@OnMessage
    public void onMessage(String message, final Session session) 
    	throws IOException, InterruptedException {
		
		// Print the client message for testing purposes
		System.out.println("Received: " + message);

		// Send 3 messages to the client every 5 seconds
		new Thread(()->{
			while(true){
				try {
					Thread.sleep(1000);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					session.getBasicRemote().sendText(df.format(Calendar.getInstance().getTime()));
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}	
		}).start();
		
		
    }
	
	/**
     * websock连接建立后触发
     * 
     * @param session
     * @param config
	 * @throws IOException 
     */
    @OnOpen
    public void OnOpen(Session session, EndpointConfig config /*, @PathParam(value = "uid") String uid*/) throws IOException {
        System.out.println("Client connected");
        
    }

    @OnClose
    public void onClose () {
    	System.out.println("Connection closed");
    }
}
