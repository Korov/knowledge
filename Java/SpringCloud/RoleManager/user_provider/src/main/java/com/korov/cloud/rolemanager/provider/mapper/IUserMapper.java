package com.korov.cloud.rolemanager.provider.mapper;

import com.korov.cloud.rolemanager.provider.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserEntity record);

    UserEntity selectByPrimaryKey(Long id);

    List<UserEntity> selectAll();

    int updateByPrimaryKey(UserEntity record);

    UserEntity selectByUserName(String userName);
}