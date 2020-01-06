package com.korov.springboot.service.determinebymethodname;

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
public class AssistService {
    @Autowired
    private IAssistMapper assisMapper;


    /**
     * 测试读取，应该使用读库
     */
    public List<Map<String, Integer>> selectUserRole(Integer userId, Integer roleId) {
        return assisMapper.selectUserRole(userId, roleId);
    }

    /**
     * 测试读取和插入,insert开头应该使用写库
     */
    public void insertAndRead(Integer userId, Integer roleId) {
        assisMapper.insertUserRole(userId, roleId);
        assisMapper.selectUserRole(userId, roleId);
    }

    /**
     * 测试读取和插入，select开头应该使用从库，应该插入失败
     *
     * @param userId
     * @param roleId
     */
    public void selectAndInsert(Integer userId, Integer roleId) {
        assisMapper.insertUserRole(userId, roleId);
        assisMapper.selectUserRole(userId, roleId);
    }

    /**
     * 测试事务能否正常工作
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertWithTran(Integer userId, Integer roleId) {
        assisMapper.insertUserRole(userId, roleId);
        throw new RuntimeException("测试事务");
    }
}
