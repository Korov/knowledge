package com.korov.springboot.BaseTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.aspect.Dancer;
import com.korov.springboot.aspect.aspectj.PerformanceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MyTest1 extends SpringbootApplicationTests {

    @Autowired
    private Dancer dancer;

    @Autowired
    @Qualifier(value = "performanceImp")
    private PerformanceImpl performance;

    @Test
    public void test() {
        dancer.perform();
        performance.perform();
    }
}
