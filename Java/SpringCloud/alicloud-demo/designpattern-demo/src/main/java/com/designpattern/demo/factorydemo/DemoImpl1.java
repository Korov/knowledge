package com.designpattern.demo.factorydemo;

import org.springframework.stereotype.Component;

@Component("one")
public class DemoImpl1 implements Demo {
    @Override
    public String doOperation() {
        return "one";
    }
}
