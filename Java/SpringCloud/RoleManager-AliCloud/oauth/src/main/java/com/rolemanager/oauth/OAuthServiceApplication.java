package com.rolemanager.oauth;

import com.rolemanager.oauth.mapper.MarkMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackageClasses = {MarkMapper.class})
public class OAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthServiceApplication.class, args);
    }
}
