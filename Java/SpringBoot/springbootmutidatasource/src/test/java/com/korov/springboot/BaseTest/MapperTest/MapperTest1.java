package com.korov.springboot.BaseTest.MapperTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.service.IAssistService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MapperTest1 extends SpringbootApplicationTests {
    @Autowired
    private IAssistService assistService;

    @Test
    public void test() {
        assistService.insertWithReadDataSource(1, 10);
        System.out.println("debug");
    }
}
