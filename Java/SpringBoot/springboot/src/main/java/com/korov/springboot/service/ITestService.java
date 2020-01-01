package com.korov.springboot.service;

import com.korov.springboot.entity.TestEntity;

import java.util.List;
import java.util.Map;

public interface ITestService {

    TestEntity printEntity();

    List<TestEntity> queryAllTest();

    void asyncTest1(int j, List<Integer> list);

    void asyncTest2(int j, List<Integer> list);

    List<Map<String, String>> queryNameEmail();
}
