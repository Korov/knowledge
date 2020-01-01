package com.korov.springboot.mapper;

import com.korov.springboot.entity.GroupEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IGroupMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(GroupEntity record);

    int insertAll(List<GroupEntity> records);

    GroupEntity selectByPrimaryKey(Integer groupId);

    List<GroupEntity> selectAll();

    List<GroupEntity> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    int updateByPrimaryKey(GroupEntity record);
}
