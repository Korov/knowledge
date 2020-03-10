package com.rolemanager.user.mapper;

import com.rolemanager.commons.model.users.GroupRoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupRoleMapper {
    int deleteByPrimaryKey(@Param("groupId") Long groupId, @Param("roleId") Long roleId);

    int insert(GroupRoleModel record);

    int insertSelective(GroupRoleModel record);

    int updateBatch(List<GroupRoleModel> list);

    int updateBatchSelective(List<GroupRoleModel> list);

    int batchInsert(@Param("list") List<GroupRoleModel> list);
}
