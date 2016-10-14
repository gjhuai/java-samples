package com.byteslounge.websockets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override  
	   protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
	         throws ServletException, IOException {  
	      this.doPost(req, resp);  
	   }  
	   
	   @Override  
	   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   String username = req.getParameter("username");
		   
		   HttpSession session = req.getSession(true);
		   session.setAttribute("LoginUserName", username);		
		   System.out.println(username + "已经登录");
	   }
}
