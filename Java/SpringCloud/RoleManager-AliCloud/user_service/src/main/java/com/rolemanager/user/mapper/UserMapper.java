package com.rolemanager.user.mapper;

import com.rolemanager.user.model.UserModel;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

    int updateBatch(List<UserModel> list);

    int updateBatchSelective(List<UserModel> list);

    int batchInsert(@Param("list") List<UserModel> list);
}