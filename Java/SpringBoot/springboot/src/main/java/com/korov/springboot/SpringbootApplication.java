package com.korov.springboot;

import com.korov.springboot.mutilbean.SingleBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBootApplication：开启组件扫描和自动配置
 *
 * @author korov9
 */
@SpringBootApplication
@MapperScan("com.korov.springboot.mapper")
@EnableTransactionManagement // 开启事务管理
@EnableAsync
@EnableAspectJAutoProxy
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    /**
     * 同一个类声明两个bean实例
     * @return
     */
    @Bean(name = "aaa")
    public SingleBean singleBean1 ()
    {
        return new SingleBean();
    }

    @Bean
    public SingleBean singleBean2 ()
    {
        return new SingleBean();
    }
}

