package com.korov.springboot.aspect;

import org.springframework.stereotype.Component;

@Component
public class Dancer implements Performance {
    @Override
    public void perform() {
        System.out.println(this.getClass().getName() + ":dancing...");
    }
}
