package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.ITestDao;
import com.korov.springboot.entity.TestEntity;
import com.korov.springboot.mapper.ITestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TestDao implements ITestDao {

    @Autowired
    private ITestMapper testMapper;

    @Override
    public TestEntity printEntity() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(18L);
        testEntity.setUserName("sss");
        return testEntity;
    }


    @Override
    public List<TestEntity> queryAllTest() {
        return testMapper.queryAllTest();
    }

    @Override
    public List<Map<String, String>> queryNameEmail() {
        return testMapper.queryNameEmail();
    }

}
