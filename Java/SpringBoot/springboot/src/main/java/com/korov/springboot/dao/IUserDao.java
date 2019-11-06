package com.korov.springboot.dao;

import com.korov.springboot.entity.UserEntity;

import java.util.List;

public interface IUserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserEntity record);

    int insertAll(List<UserEntity> records);

    UserEntity selectByPrimaryKey(Integer userId);

    UserEntity selectByUsername(String name);

    List<UserEntity> selectAll();

    List<UserEntity> selectPage(Integer pageNum, Integer pageSize);

    int updateByPrimaryKey(UserEntity record);
}
