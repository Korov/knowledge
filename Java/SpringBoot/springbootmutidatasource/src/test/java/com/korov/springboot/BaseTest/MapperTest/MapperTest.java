package com.korov.springboot.BaseTest.MapperTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.service.IAssistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MapperTest extends SpringbootApplicationTests {
    @Autowired
    private IAssistService assistService;

    @Test
    public void test() {
        assistService.testReadAndWrite(1, 9);
        List<Map<String, Integer>> result = assistService.read(1, 9);
        assistService.transInsert(1, 10);
        List<Map<String, Integer>> result1 = assistService.read(1, 10);
        System.out.println("debug");
    }
}
