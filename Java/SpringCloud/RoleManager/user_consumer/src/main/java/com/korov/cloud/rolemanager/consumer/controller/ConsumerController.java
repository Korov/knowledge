package com.korov.cloud.rolemanager.consumer.controller;

import com.korov.cloud.rolemanager.provider.UserEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@Slf4j
public class ConsumerController {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private static final String KOROV_PROVIDER_URL = "http://rolemanager-provider";

    @GetMapping("/consumer/{userId}")
    @HystrixCommand(fallbackMethod = "get_fallbackMethod")
    public UserEntity findById1(@PathVariable long userId) {
        return this.restTemplate.getForObject(KOROV_PROVIDER_URL + "/" + userId, UserEntity.class);
    }

    public UserEntity get_fallbackMethod(@PathVariable long userId) {
        UserEntity entity = new UserEntity();
        entity.setId(0L).setAge(0).setName("not find");
        return entity;
    }

    @PostMapping("/consumer/rabbitMq/addUser")
    public void findById1(@RequestBody String user) {
        this.rabbitTemplate.convertAndSend("user", user);
    }

    @RequestMapping("/test")
    public String test(HttpServletRequest request) {
        System.out.println("----------------success access test method!----------------");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            System.out.println(key + ": " + request.getHeader(key));
        }
        return "success access test method!!";
    }

    @RequestMapping("/accessProvider")
    public String accessProvider(HttpServletRequest request) {
        String result = restTemplate.getForObject(KOROV_PROVIDER_URL + "/provider/test", String.class);
        return result;
    }
}
