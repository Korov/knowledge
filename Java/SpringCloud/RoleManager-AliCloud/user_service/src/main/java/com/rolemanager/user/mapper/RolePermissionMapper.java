package com.rolemanager.user.mapper;

import com.rolemanager.commons.model.RolePermissionModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    int insert(RolePermissionModel record);

    int insertSelective(RolePermissionModel record);

    int updateBatch(List<RolePermissionModel> list);

    int updateBatchSelective(List<RolePermissionModel> list);

    int batchInsert(@Param("list") List<RolePermissionModel> list);
}
