package com.rolemanager.user.mapper;

import com.rolemanager.commons.model.users.UserRoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("userId") Integer userId);

    int insert(UserRoleModel record);

    int insertSelective(UserRoleModel record);

    int updateBatch(List<UserRoleModel> list);

    int updateBatchSelective(List<UserRoleModel> list);

    int batchInsert(@Param("list") List<UserRoleModel> list);
}
