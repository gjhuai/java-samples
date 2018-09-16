package com.example.demo.model;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: AnakinSky
 * Date: 2018/5/14
 * Time: 16:21
 **/
@Data
public class City {
    long id;
    int province_id;
    String city_name;
    String description;
}
