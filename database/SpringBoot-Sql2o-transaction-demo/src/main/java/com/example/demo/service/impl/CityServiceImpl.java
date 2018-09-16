package com.example.demo.service.impl;

import com.example.demo.model.City;
import com.example.demo.service.CityService;

import lombok.val;

import java.util.HashMap;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 * Created by IntelliJ IDEA.
 * User: AnakinSky
 * Date: 2018/5/14
 * Time: 16:24
 **/
@Service
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired
    private Sql2o sql2o;
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
	final String insertQuery = "INSERT INTO city (id, province_id, city_name) "
			+ "VALUES (:id, :province_id, :city_name)";

    @Override
    public City findCityByName(String name) {
        String sql = "SELECT * FROM city WHERE city_name = :name";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("name",name).executeAndFetchFirst(City.class);
        }
    }

    @Override
    public City findCityById(int id) {
        String sql = "SELECT * FROM city WHERE province_id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(City.class);
        }
    }
    
	public void insertCity(City city) {
		try (Connection con = sql2o.open()) {
			con.createQuery(insertQuery).addParameter("id", city.getId())
					.addParameter("province_id", city.getProvince_id())
					.addParameter("city_name", city.getCity_name())
					.executeUpdate();
		}

		// 不能回滚
		if (1==1) {
			throw new IllegalArgumentException();
		}

		String city_name = "河南";
		city = this.findCityByName(city_name);
		if (city != null) {
			System.out.print(city.toString());
		}
	}
	
	public void insertCity2(City city) {
		
		val params = new HashMap<String, Object>();
		params.put("id", city.getId());
		params.put("province_id", city.getProvince_id());
		params.put("city_name", city.getCity_name());

		jdbcTemplate.update(insertQuery, params);

		// 可以回滚
		if (1==1) {
			throw new IllegalArgumentException();
		}

		String city_name = "河南";
		city = this.findCityByName(city_name);
		if (city != null) {
			System.out.print(city.toString());
		}
	}
}
