package com.designpattern.demo.factorydemo;

import com.designpattern.demo.ApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FactoryTest extends ApplicationTests {
    @Autowired
    private FactoryDemo factoryDemo;

    @Test
    public void test1() {
        Demo strategy = factoryDemo.getDemo("one");
        String value = strategy.doOperation();
        System.out.println(value);
    }
}
