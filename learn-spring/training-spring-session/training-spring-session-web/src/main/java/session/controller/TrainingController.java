package session.controller;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrainingController {

	public TrainingController() {
		System.out.println("===============");
	}

	@ResponseBody
	@RequestMapping("/set")
	public String set(HttpSession session, HttpServletResponse response) {
		String cookieName = "SESSION";
		String sessionVal = "545e52ab-ecb0-4a93-a21e-4d01d481d2c7";
		
		// 针对IE，必须加P3P到header中，否则无法写入cookie
		response.addHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\""); 

		Cookie cookie = new Cookie(cookieName, sessionVal);
        cookie.setMaxAge(-1);   	// 浏览器关闭此Cookie时效
        cookie.setPath("/");
        response.addCookie(cookie); //加入Cookies
        
		Random r = new Random();
		session.setAttribute("temp:" + r.nextLong(), UUID.randomUUID()
				.toString());
		String val = "Hello World";
		session.setAttribute("aHello", val);
		return "setValue:"+val;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public String get(HttpSession session) {
		Object obj = session.getAttribute("aHello");
		obj = obj==null?"no value":obj;
		return "getValue:"+obj;
	}
	
}
