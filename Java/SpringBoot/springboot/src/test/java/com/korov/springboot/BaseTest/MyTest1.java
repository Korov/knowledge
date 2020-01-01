package com.korov.springboot.BaseTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.aspect.Audience;
import com.korov.springboot.aspect.AudiencePointcut;
import com.korov.springboot.aspect.Dancer;
import com.korov.springboot.aspect.aspectj.PerformanceImpl;
import com.korov.springboot.entity.UserEntity;
import com.korov.springboot.mapper.IAssistMapper;
import com.korov.springboot.service.ITestService;
import com.korov.springboot.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
