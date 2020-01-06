package com.korov.springboot.mapper.slave;

import com.korov.springboot.entity.TestEntity;

import java.util.List;
import java.util.Map;

public interface TestMapper {
    List<TestEntity> queryAllTest();

    List<Map<String, String>> queryNameEmail();
}
