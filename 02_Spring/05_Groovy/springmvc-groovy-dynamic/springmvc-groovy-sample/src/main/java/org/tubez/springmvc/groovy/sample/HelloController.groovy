package org.tubez.springmvc.groovy.sample;

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping(value="/hello")
class HelloController {

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ResponseBody
	def append(HttpSession session){
		session.setAttribute("helloworld", "1122334455")
		return "agent22wwrr"
	}

	@RequestMapping(value="/pp")
	def index(HttpServletRequest req, Model model, HttpSession session){
		println "==="+session.getAttribute("helloworld")
		def mapData = [key1:'value-1', key2:'值-2', key3:'value-3', key4:'value-4']
		model.addAttribute("mapData", mapData)
		
		def list = ['value-1', 'value-2', 'value-3', 'value-4']
		model.addAttribute("listData", list)
		
		return "hello"
	}
	
}
