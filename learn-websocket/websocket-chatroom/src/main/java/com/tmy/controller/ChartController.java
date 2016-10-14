package com.tmy.controller;

import java.nio.file.AccessDeniedException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmy.model.User;
import com.tmy.repository.ParticipantRepository;

/**
 * 
 * @author Cliff
 */
@Controller
public class ChartController {
    
    @Autowired ParticipantRepository participantRepository;
    
    @RequestMapping(value="/chart", method=RequestMethod.GET)
    public String chartPage(HttpServletRequest request, Model model) throws AccessDeniedException{
        if(request.getSession().getAttribute("user") == null){
            throw new AccessDeniedException("login please");
        }
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("username", user.getUsername());
        return "chart";
    }
    
    @SubscribeMapping("/chat.participants")
    public Collection<User> retrieveParticipants() {
        return participantRepository.getActiveSessions().values();
    }

}
