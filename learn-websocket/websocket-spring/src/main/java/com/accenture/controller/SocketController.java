package com.accenture.controller;

import com.accenture.socket.SocketHandler;
import com.accenture.socket.TimerSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;

/**
 * @desp Socket控制器
 * @author liulichao@ruc.edu.cn
 * @date 2016-5-6
 *
 */
@Controller
public class SocketController{
	
	private static final Logger logger = LoggerFactory.getLogger(SocketController.class);
	
	@Autowired
	private SocketHandler socketHandler;

	@Autowired
	private TimerSocketHandler timerSocketHandler;
	
	@RequestMapping(value="/")
	public String login(HttpSession session){
		logger.info("用户登录了建立连接啦");
		
		session.setAttribute("user", "liulichao");
		
		return "home";
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String sendMessage() throws InterruptedException{
		socketHandler.sendMessageToUser("liulichao", new TextMessage("这是一条测试的消息"));

		return "message";
	}

	@RequestMapping(value="/timepage")
	public String timePage(HttpSession session){
		session.setAttribute("user", "liulichao");
		return "time_page";
	}

//	@RequestMapping(value = "/timer", method = RequestMethod.GET)
//	public String timer() throws InterruptedException{
//		new Thread(()->{
//		while(true){
//			Thread.sleep(5000);
//			socketHandler.sendMessageToUser("liulichao", new TextMessage("这是一条测试的消息"));
//		}
//		}).start();
//
//		return "message";
//	}

}
