package com.blocks.websocket.services;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.blocks.websocket.config.Constants;

public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(HttpSessionIdHandshakeInterceptor.class);
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) throws Exception {
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		HttpSession session = servletRequest.getServletRequest().getSession(false);
		
		if (session != null) {
			String username = (String)session.getAttribute(Constants.CURRENT_USER_NAME);
//			attributes.put("HTTPSESSIONID", session.getId());
			
			if (username!=null){
				ServletContext sc = SpringUtil.getServletContext();
				@SuppressWarnings("unchecked")
				Set<String> userSet = (Set<String>)sc.getAttribute(Constants.CURRENT_USER_LIST);
				if (userSet==null){
					userSet = new CopyOnWriteArraySet<>();
	        		sc.setAttribute("CURRENT_USER_LIST", userSet);
	        	}
				userSet.add(username);
				attributes.put(Constants.CURRENT_USER_NAME, username);
				logger.debug("Session Interceptor : CURRENT_USER_LIST:"+ userSet);
			}
		}
		
		return true;
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
	}
}