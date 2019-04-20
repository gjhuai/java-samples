package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.City;

@Controller
@RequestMapping("/city")
public class TestController{
	@Autowired
	CityService cityService;
	
	// http://localhost:8080/city?id=1&province_id=11&city_name=sc
	@RequestMapping
	public String insert(City city) {
		cityService.insertCity(city);
		return "ok";
	}
	@RequestMapping("/jdbc")
	public String insert2(City city) {
		cityService.insertCity2(city);
		return "ok";
	}
}