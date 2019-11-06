package com.korov.springboot.dao;

import com.korov.springboot.entity.TestEntity;

import java.util.List;
import java.util.Map;

public interface ITestDao {
    TestEntity printEntity();

    List<TestEntity> queryAllTest();

    List<Map<String, String>> queryNameEmail();
}
