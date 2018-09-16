package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: AnakinSky
 * Date: 2018/5/14
 * Time: 16:07
 **/
@Component
@ConfigurationProperties(prefix = "ds.datasource")
@Data
public class DSProperties {
    String url;
    String username;
    String password;
    String driver;
}
