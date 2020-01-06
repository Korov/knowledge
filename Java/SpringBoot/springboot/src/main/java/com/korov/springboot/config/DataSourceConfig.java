package com.korov.springboot.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Autowired
    Environment env;

    @Primary
    @Bean(name = "dataSourceMaster")
    public DataSource dataSourceMaster() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.master.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.master.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.master.password"));
        dataSource.setDriverClassName(env.getProperty("spring.datasource.master.driver-class-name"));
        return dataSource;
    }

    @Bean(name = "dataSourceSlave")
    public DataSource dataSourceSlave() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.slave.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.slave.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.slave.password"));
        dataSource.setDriverClassName(env.getProperty("spring.datasource.slave.driver-class-name"));
        return dataSource;
    }
}
