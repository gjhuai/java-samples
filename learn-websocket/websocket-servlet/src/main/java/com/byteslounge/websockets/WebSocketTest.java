package com.byteslounge.websockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket", configurator = WsConfigurator.class)
public class WebSocketTest {
	//连接集合
    private static final Set<WebSocketTest> connections = new CopyOnWriteArraySet<WebSocketTest>();
    
    private Session session;
    private String username = null;

	public static Map<String,String> msgMap = new HashMap<>();
	static {
		msgMap.put("user1", "Hello User1");
		msgMap.put("user2", "Hello User2");
	}
	
	private String user = null;

	@OnMessage
    public void onMessage(String message, Session session) 
    	throws IOException, InterruptedException {
		
		user = message;
		System.out.println("user:" + user);
		
		// Print the client message for testing purposes
		System.out.println("Received: " + message);
		
		// Send the first message to the client
		session.getBasicRemote().sendText("This is the first server message");
		
		// Send 3 messages to the client every 5 seconds
		int sentMessages = 0;
		while(sentMessages < 3){
			Thread.sleep(5000);
			session.getBasicRemote().
				sendText("This is an intermediate server message. Count: " 
					+ sentMessages);
			sentMessages++;
		}
		
		// Send a final message to the client
		session.getBasicRemote().sendText("This is the last server message:"+user);
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
        //设置websock连接的session
        setSession(session);
        // 获取HttpSession
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        // 从HttpSession中取得当前登录的用户作为当前连接的用户
        username = (String)httpSession.getAttribute("LoginUserName");
        
        if (username == null) {
        	System.err.println("用户没登录");
            return;
        }
        
        //加入连接集合
        getConnections().add(this);
        
        //广播通知所有连接有新用户加入
        this.getSession().getBasicRemote().sendText(msgMap.get(username));
        System.out.println("Client connected");
        
    }

    public void sendMsg(String msg) throws IOException{
    	this.getSession().getBasicRemote().sendText(msg);
    }

    @OnClose
    public void onClose () {
    	System.out.println("Connection closed");
    }
    
   

	public String getUsername() {
		return username;
	}

	public Session getSession() {
		return session;
	}
    public void setSession(Session session) {
        this.session = session;
    }
	
	public static Set<WebSocketTest> getConnections() {
		return connections;
	}
}
