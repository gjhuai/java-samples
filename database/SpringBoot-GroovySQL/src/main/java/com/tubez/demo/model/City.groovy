package com.tubez.demo.model

import gstorm.Id;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: AnakinSky
 * Date: 2018/5/14
 * Time: 16:21
 **/
class City {
    @Id
    Integer id;
    int province_id;
    String city_name;
    String description;
}
