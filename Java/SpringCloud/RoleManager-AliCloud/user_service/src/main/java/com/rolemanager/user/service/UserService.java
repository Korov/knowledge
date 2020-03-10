package com.rolemanager.user.service;

import com.rolemanager.commons.model.users.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Long id);

    int insert(UserModel record);

    UserModel selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserModel record);

    int updateByPrimaryKeySelective(UserModel record);

    int updateBatch(List<UserModel> list);

    int batchInsert(@Param("list") List<UserModel> list);

    List<UserModel> getUsers(@Param("index") long index, @Param("length") long length);

    long getUserNum();

    UserModel getUserByName(String userName);
}
