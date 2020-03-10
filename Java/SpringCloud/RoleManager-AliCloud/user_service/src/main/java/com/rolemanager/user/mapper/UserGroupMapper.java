package com.rolemanager.user.mapper;

import com.rolemanager.commons.model.UserGroupModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("groupId") Long groupId);

    int insert(UserGroupModel record);

    int insertSelective(UserGroupModel record);

    int updateBatch(List<UserGroupModel> list);

    int updateBatchSelective(List<UserGroupModel> list);

    int batchInsert(@Param("list") List<UserGroupModel> list);
}
