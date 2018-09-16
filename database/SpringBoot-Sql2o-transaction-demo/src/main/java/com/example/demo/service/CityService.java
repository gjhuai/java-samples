package com.example.demo.service;

import com.example.demo.model.City;

/**
 * Created by IntelliJ IDEA.
 * User: AnakinSky
 * Date: 2018/5/14
 * Time: 16:22
 **/
public interface CityService {
    City findCityByName(String name);
    City findCityById(int id);
    void insertCity(City city);
    void insertCity2(City city);
}
