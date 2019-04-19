package com.yuan.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpleService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> getInfo(){
		String sql = "select * from user";
		return jdbcTemplate.queryForList(sql);
	}
}
