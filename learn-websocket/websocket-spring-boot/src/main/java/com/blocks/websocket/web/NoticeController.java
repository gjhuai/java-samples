package com.blocks.websocket.web;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blocks.websocket.config.Constants;
import com.blocks.websocket.services.SpringUtil;

@Controller
public class NoticeController { 
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @RequestMapping("/login")
    public String mockLogin(HttpSession session){
    	session.setAttribute(Constants.CURRENT_USER_NAME, "user1");
    	
    	return "redirect:/postNotice.html";
    }
    
    @MessageMapping("/post-notice")
    public void greeting(String value){
    	ServletContext sc = SpringUtil.getServletContext();
		@SuppressWarnings("unchecked")
		Set<String> userSet = (Set<String>)sc.getAttribute("CURRENT_USER_LIST");
		
		for (String username : userSet){
			String dest = "/topic/notice/"+username;
			this.simpMessagingTemplate.convertAndSend(dest, value);
		}
    }
}
