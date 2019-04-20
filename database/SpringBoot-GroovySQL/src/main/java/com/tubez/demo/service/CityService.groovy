package com.tubez.demo.service;

import com.tubez.demo.model.City;
import com.tubez.demo.service.CityService
import groovy.sql.Sql;
import gstorm.Gstorm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: guanjh
 * Date: 2018/5/14
 * Time: 16:24
 **/
@Service
@Transactional
public class CityService {

    @Autowired
    private Gstorm gstorm;
    @Autowired
    private Sql sql ;

    final String insertQuery = """INSERT INTO city (id, province_id, city_name)
            VALUES (:id, :province_id, :city_name)""";

    public City findCityByName(String name) {
        return City.where("city_name=$name");
    }

    public City findCityById(int id) {
        String sql = "SELECT * FROM city WHERE province_id = ?";
        City city = gstorm.sql.firstRow(sql)
        return city;
    }

    public void insertCity(City city) {
        city.save();

//        sql.executeInsert("""INSERT INTO city (id, province_id, city_name)
//            VALUES (${city.id}, ${city.province_id}, ${city.city_name})""")

//        sql.execute(insertQuery, city)

//        sql.execute(insertQuery, id:city.id, province_id:city.province_id, city_name:city.city_name)

        // 回滚
//        if (1==1) {
//            throw new IllegalArgumentException();
//        }
//
//        String city_name = "河南";
//        city = this.findCityByName(city_name);
//        if (city != null) {
//            System.out.print(city.toString());
//        }
    }

}
