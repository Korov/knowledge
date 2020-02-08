package com.designpattern.demo.factorydemo;

import org.springframework.stereotype.Component;

@Component("two")
public class DemoImpl2 implements Demo {
    @Override
    public String doOperation() {
        return "two";
    }
}
