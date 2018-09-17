package com.tubez.demo.config;

import com.tubez.demo.model.City;
import groovy.sql.Sql;
import gstorm.Gstorm;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return defaultDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public TransactionAwareDataSourceProxy dataSourceProxy(){
        TransactionAwareDataSourceProxy dataSourceProxy = new TransactionAwareDataSourceProxy();
        dataSourceProxy.setTargetDataSource(dataSource());
        return dataSourceProxy;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSourceProxy());
        return txManager;
    }

    @Bean
    public Sql sql() {
        return new Sql(dataSourceProxy());
    }

    @Bean
    public Gstorm gstorm(){
        Gstorm gstorm = new Gstorm(sql());
        gstorm.enhance(City.class);
        return gstorm;
    }
}