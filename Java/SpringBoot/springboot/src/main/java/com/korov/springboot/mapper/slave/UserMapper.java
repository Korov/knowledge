package com.korov.springboot.mapper.slave;

import com.korov.springboot.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserEntity record);

    int insertAll(List<UserEntity> records);

    UserEntity selectByPrimaryKey(Integer userId);

    UserEntity selectByUsername(String name);

    List<UserEntity> selectAll();

    List<UserEntity> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    int updateByPrimaryKey(UserEntity record);
}
