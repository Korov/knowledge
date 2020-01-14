package com.redis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {
    @Autowired
    private Environment environment;

    @GetMapping(value = "/applicationName")
    public String demo() {
        return environment.getProperty("spring.application.name");
    }
}
