package com.example.demo.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: AnakinSky
 * Date: 2018/5/14
 * Time: 15:51
 **/
@Configuration
@EnableTransactionManagement
public class DSConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private DSProperties dsProperties;
    
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSourceProxy());
    }

    @Bean
    public TransactionAwareDataSourceProxy dataSourceProxy(){
        TransactionAwareDataSourceProxy dataSourceProxy = new TransactionAwareDataSourceProxy();
        dataSourceProxy.setTargetDataSource(dataSource());
        return dataSourceProxy;
    }
    
    @Bean
    public DataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dsProperties.getDriver());
        dataSource.setUrl(dsProperties.getUrl());
        dataSource.setUsername(dsProperties.getUsername());
        dataSource.setPassword(dsProperties.getPassword());
        return dataSource;
    }

    @Bean
    public Sql2o sql2o() {
        return new Sql2o(dataSourceProxy());
    }

    @Bean
    public NamedParameterJdbcTemplate npJdbcTemplate(){
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(dataSource());
        return npjt;
    }
}
