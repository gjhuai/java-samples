package com.tubez.demo;

import com.tubez.demo.model.City;
import com.tubez.demo.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    @Rollback(false)
    public void insertCity() {
//    	City city = new City("id":2, "province_id": 111, "city_name": "河南");
    	def city = new City("province_id": 111, "city_name": "河南");

    	cityService.insertCity(city);
    }

}
