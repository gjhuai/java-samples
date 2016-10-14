package com.accenture.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desp Socket处理类
 * @author liulichao@ruc.edu.cn
 *
 */
@Service
public class TimerSocketHandler implements WebSocketHandler{
	private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("成功建立socket连接");
		new Thread(()->{
			while(true) {
				try {
					Thread.sleep(1000);

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					session.sendMessage(new TextMessage(df.format(new Date())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> arg1) throws Exception {
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable error)	throws Exception {
		if(session.isOpen()){
			session.close();
		}
		logger.error("连接出现错误:"+error.toString());
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		logger.debug("连接已关闭");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
