package com.tubez.demo.web;

import com.tubez.demo.model.City;
import com.tubez.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/city")
public class CityController {
	@Autowired
	CityService cityService;
	
	// http://localhost:8080/city?id=1&province_id=11&city_name=sc
	@RequestMapping
	public String insert(City city) {
		cityService.insertCity(city);
		return "ok";
	}

}