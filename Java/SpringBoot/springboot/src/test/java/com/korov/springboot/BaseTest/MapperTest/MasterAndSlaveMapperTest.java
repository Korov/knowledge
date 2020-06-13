package com.korov.springboot.BaseTest.MapperTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.mapper.master.IAssistMapper;
import com.korov.springboot.mapper.slave.AssistMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MasterAndSlaveMapperTest extends SpringbootApplicationTests {
    @Autowired
    private IAssistMapper masterAssistMapper;

    @Autowired
    private AssistMapper slaveAssistMapper;

    @Test
    public void mapperTest() {
        List<Map<String, Integer>> masterUserRole = masterAssistMapper.selectUserRole(1, 2);
        List<Map<String, Integer>> slaveUserRole = slaveAssistMapper.selectUserRole(1, 5);
        System.out.println("debug");
    }
}
