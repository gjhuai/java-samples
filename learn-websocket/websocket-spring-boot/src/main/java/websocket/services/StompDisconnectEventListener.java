package websocket.services;

import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import websocket.config.Constants;

@Component
public class StompDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

	private static final Logger logger = LoggerFactory.getLogger(StompDisconnectEventListener.class);

	@Override
	public void onApplicationEvent(SessionDisconnectEvent sessionSubscribeEvent) {
		ServletContext sc = SpringUtil.getServletContext();
		@SuppressWarnings("unchecked")
		Set<String> userSet = (Set<String>)sc.getAttribute(Constants.CURRENT_USER_LIST);
		logger.debug("Before Disconnect CURRENT_USER_LIST:"+userSet);
		
		if (userSet!=null && userSet.size()!=0){
			StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
			String username = headerAccessor.getSessionAttributes().get(Constants.CURRENT_USER_NAME).toString();
			userSet.remove(username);
			logger.debug("After Disconnect CURRENT_USER_LIST£º"+userSet);
		}
	}
}
