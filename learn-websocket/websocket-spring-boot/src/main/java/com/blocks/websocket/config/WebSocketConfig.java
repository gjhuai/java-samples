package com.blocks.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.blocks.websocket.services.HttpSessionIdHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").withSockJS().setInterceptors(httpSessionIdHandshakeInterceptor());
//    	registry.addEndpoint("/hello").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // use the /topic prefix for outgoing WebSocket communication
        config.enableSimpleBroker("/topic");

        // use the /app prefix for others
        config.setApplicationDestinationPrefixes("/app");

    }
    
    @Bean
    public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
    	return new HttpSessionIdHandshakeInterceptor();
    }
}
