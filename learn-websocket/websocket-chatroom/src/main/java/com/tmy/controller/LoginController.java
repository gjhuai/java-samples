package com.tmy.controller;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmy.model.User;
import com.tmy.repository.ParticipantRepository;

/**
 * 
 * @author Cliff
 */
@Controller
public class LoginController {
    
    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private ParticipantRepository participantRepository;

    private static final String LOGIN = "/app/chat.login";
    private static final String LOGOUT = "/app/chat.logout";
    
    @RequestMapping(value = "login", method=RequestMethod.POST)
    public String login(HttpServletRequest httpRequest, User user) throws ServletException{
        user.setTime(new Date());
        httpRequest.getSession().setAttribute("user", user);
        messagingTemplate.convertAndSend(LOGIN, user);
        if(participantRepository.getActiveSessions().containsKey(httpRequest.getSession().getId())){
            messagingTemplate.convertAndSend(LOGOUT, participantRepository.getActiveSessions().get(httpRequest.getSession().getId()));
        }
        participantRepository.add(httpRequest.getSession().getId(), user);
        return "redirect:/chart";
    }

}
