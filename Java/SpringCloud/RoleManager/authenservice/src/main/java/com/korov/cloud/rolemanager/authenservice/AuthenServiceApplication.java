package com.korov.cloud.rolemanager.authenservice;

import com.korov.cloud.rolemanager.authenservice.intercepter.RestTemplateUserContextInterceptor;
import com.korov.cloud.rolemanager.authenservice.intercepter.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableWebMvc
public class AuthenServiceApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(AuthenServiceApplication.class, args);
    }
    /**
     * 请求拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor());
    }

    /***
     * RestTemplate 拦截器，在发送请求前设置鉴权的用户上下文信息
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new RestTemplateUserContextInterceptor());
        return restTemplate;
    }
}
