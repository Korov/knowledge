package com.korov.springboot.mapper.master;

import com.korov.springboot.entity.TestEntity;

import java.util.List;
import java.util.Map;

public interface ITestMapper {
    List<TestEntity> queryAllTest();

    List<Map<String, String>> queryNameEmail();
}
