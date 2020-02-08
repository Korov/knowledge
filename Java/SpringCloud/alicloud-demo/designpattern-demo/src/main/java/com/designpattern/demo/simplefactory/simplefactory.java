package com.designpattern.demo.simplefactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class simplefactory {
    @Bean(value = "bean1")
    public Demo1 getBean() {
        return new Demo1();
    }

    @Bean(value = "bean2")
    public Demo1 getBean1() {
        return new Demo1();
    }
}
