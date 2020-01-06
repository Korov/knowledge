package com.korov.springboot.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.korov.springboot.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
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
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource dataSourceSlave() {
        return new DruidDataSource();
    }

    @Bean
    public AbstractRoutingDataSource routingDataSource() {
        Map targetDataSources = new HashMap<>();
        // 目前有两个数据库，一个master和一个slave，slave的key为slave1，再多的话自己配置
        targetDataSources.put(DbContextHolder.MASTER, dataSourceMaster());
        targetDataSources.put(DbContextHolder.SLAVE + "1", dataSourceSlave());

        MyAbstractRoutingDataSource myRoutingDataSource = new MyAbstractRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(dataSourceMaster());
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }

    /**
     * 多数据源需要自己设置sqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(routingDataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 实体类对应的位置
        bean.setTypeAliasesPackage("com.korov.springboot.entity");
        // mybatis的XML的配置
        bean.setMapperLocations(resolver.getResources("classpath:mapper/*Mapper.xml"));
        return bean.getObject();
    }

    /**
     * 设置事务，事务需要知道当前使用的是哪个数据源才能进行事务处理
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(routingDataSource());
    }
}
