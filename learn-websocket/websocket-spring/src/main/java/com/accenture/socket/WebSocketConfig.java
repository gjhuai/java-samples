package com.accenture.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @desp websocket配置
 * @author liulichao@ruc.edu.cn
 *
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	
	@Autowired
	private SocketHandler socketHandler;
	@Autowired
	private TimerSocketHandler timerSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		//注册处理拦截器,拦截url为socketServer的请求
		registry.addHandler(socketHandler, "/socketServer").addInterceptors(new WebSocketInterceptor());
		
		//注册SockJs的处理拦截器,拦截url为/sockjs/socketServer的请求
		registry.addHandler(socketHandler, "/sockjs/socketServer").addInterceptors(new WebSocketInterceptor()).withSockJS();

		//注册处理拦截器,拦截url为socketServer的请求
		registry.addHandler(timerSocketHandler, "/timer");

		//注册SockJs的处理拦截器,拦截url为/sockjs/socketServer的请求
		registry.addHandler(timerSocketHandler, "/sockjs/timer").withSockJS();
	}

}
