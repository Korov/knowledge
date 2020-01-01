package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.IUserDao;
import com.korov.springboot.entity.UserEntity;
import com.korov.springboot.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements IUserDao {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(UserEntity record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertAll(List<UserEntity> records) {
        return userMapper.insertAll(records);
    }

    @Override
    public UserEntity selectByPrimaryKey(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public UserEntity selectByUsername(String name) {
        return userMapper.selectByUsername(name);
    }

    @Override
    public List<UserEntity> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<UserEntity> selectPage(Integer pageNum, Integer pageSize) {
        return userMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(UserEntity record) {
        return userMapper.updateByPrimaryKey(record);
    }

}
