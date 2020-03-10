package com.rolemanager.user.mapper;

import com.rolemanager.commons.model.users.RoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleModelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleModel record);

    int insertSelective(RoleModel record);

    RoleModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleModel record);

    int updateByPrimaryKey(RoleModel record);

    int updateBatch(List<RoleModel> list);

    int updateBatchSelective(List<RoleModel> list);

    int batchInsert(@Param("list") List<RoleModel> list);
}
