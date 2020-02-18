package com.rolemanager.user.service.impl;

import com.rolemanager.user.mapper.UserMapper;
import com.rolemanager.user.model.UserModel;
import com.rolemanager.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        if (id == null) {
            log.error("deleteByPrimaryKey id is null");
            return -1;
        }
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserModel record) {
        return userMapper.insert(record);
    }

    @Override
    public UserModel selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UserModel record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(UserModel record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateBatch(List<UserModel> list) {
        return userMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<UserModel> list) {
        return userMapper.batchInsert(list);
    }

    @Override
    public List<UserModel> getUsers(long pagenum, long pagesize) {
        long index = (pagenum -1) * pagesize;
        long length = pagesize;
        return userMapper.getUsers(index, length);
    }

    @Override
    public long getUserNum() {
        return userMapper.getUserNum();
    }
}
