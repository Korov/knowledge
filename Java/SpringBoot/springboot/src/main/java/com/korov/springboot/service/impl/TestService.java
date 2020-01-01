package com.korov.springboot.service.impl;

import com.korov.springboot.dao.ITestDao;
import com.korov.springboot.entity.TestEntity;
import com.korov.springboot.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TestService implements ITestService {

    private static int i = 0;
    private static List<Integer> myList = new ArrayList<>(200);
    @Autowired
    private ITestDao testDao;

    @Override
    public TestEntity printEntity() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(18L);
        testEntity.setUserName("sss");
        return testEntity;
    }

    @Override
    public List<TestEntity> queryAllTest() {
        return testDao.queryAllTest();
    }

    @Override
    @Async
    public void asyncTest1(int j, List<Integer> list) {
        ++i;
        myList.add(i);
        list = myList;
        System.out.println("异步任务1：" + i);
    }

    @Override
    @Async
    public void asyncTest2(int j, List<Integer> list) {
        ++i;
        myList.add(i);
        list = myList;
        System.out.println("异步任务1：" + i);
    }

    @Override
    public List<Map<String, String>> queryNameEmail() {
        return testDao.queryNameEmail();
    }

}
