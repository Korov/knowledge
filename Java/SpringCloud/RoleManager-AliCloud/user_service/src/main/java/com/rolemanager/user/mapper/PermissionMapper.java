package com.rolemanager.user.mapper;

import com.rolemanager.commons.model.users.PermissionModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PermissionModel record);

    int insertSelective(PermissionModel record);

    PermissionModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionModel record);

    int updateByPrimaryKey(PermissionModel record);

    int updateBatch(List<PermissionModel> list);

    int updateBatchSelective(List<PermissionModel> list);

    int batchInsert(@Param("list") List<PermissionModel> list);
}
