package com.korov.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBootApplication：开启组件扫描和自动配置
 *
 * @author korov9
 */
@SpringBootApplication
@EnableTransactionManagement // 开启事务管理
@EnableAsync
@EnableAspectJAutoProxy
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}

