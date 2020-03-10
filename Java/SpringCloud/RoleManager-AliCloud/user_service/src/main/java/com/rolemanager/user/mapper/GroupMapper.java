package com.rolemanager.user.mapper;

import com.rolemanager.commons.model.GroupModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupModel record);

    int insertSelective(GroupModel record);

    GroupModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupModel record);

    int updateByPrimaryKey(GroupModel record);

    int updateBatch(List<GroupModel> list);

    int updateBatchSelective(List<GroupModel> list);

    int batchInsert(@Param("list") List<GroupModel> list);
}
