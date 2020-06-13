package com.designpattern.demo.simplefactory;

import com.designpattern.demo.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Test1 extends ApplicationTests {
    @Autowired
    @Qualifier(value = "bean2")
    private Demo1 demo1;

    @Test
    public void test1() {
        String value = demo1.getValue("korov");
        System.out.println(value);
    }
}
