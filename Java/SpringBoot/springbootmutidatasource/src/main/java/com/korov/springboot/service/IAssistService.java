package com.korov.springboot.service;

import com.korov.springboot.config.ReadOnly;
import com.korov.springboot.mapper.IAssistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author fxb
 * @date 2018-09-04
 */
@Service
public class IAssistService {
    @Autowired
    private IAssistMapper assisMapper;


    /**
     * 测试读取，应该使用读库
     */
    @ReadOnly
    public List<Map<String, Integer>> read(Integer userId, Integer roleId) {
        return assisMapper.selectUserRole(userId, roleId);
    }

    /**
     * 测试使用读数据库插入，应该失败
     */
    @ReadOnly
    public void insertWithReadDataSource(Integer userId, Integer roleId) {
        assisMapper.insertUserRole(userId, roleId);
    }

    /**
     * 测试读取和插入,应该使用写库
     */
    public void testReadAndWrite(Integer userId, Integer roleId) {
        assisMapper.insertUserRole(userId, roleId);
        assisMapper.selectUserRole(userId, roleId);
    }

    /**
     * 测试事务能否正常工作
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void transInsert(Integer userId, Integer roleId) {
        assisMapper.insertUserRole(userId, roleId);
        throw new RuntimeException("测试事务");
    }
}
