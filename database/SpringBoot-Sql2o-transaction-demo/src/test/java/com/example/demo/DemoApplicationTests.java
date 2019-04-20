package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.City;
import com.example.demo.service.CityService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DemoApplicationTests {

    @Autowired
    private CityService cityService;
    @Test
    public void contextLoads() {
    }

    @Test
    @Rollback(true)
    public void test() {
    	City city = new City();
    	city.setId(2);
    	city.setProvince_id(111);
    	city.setCity_name("河南");
    	
    	cityService.insertCity(city);
    }

}
