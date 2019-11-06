package com.korov.springboot.dao;

import com.korov.springboot.entity.GroupEntity;

import java.util.List;

public interface IGroupDao {
    int deleteByPrimaryKey(Integer groupId);

    int insert(GroupEntity record);

    int insertAll(List<GroupEntity> records);

    GroupEntity selectByPrimaryKey(Integer groupId);

    List<GroupEntity> selectAll();

    List<GroupEntity> selectPage(Integer pageNum, Integer pageSize);

    int updateByPrimaryKey(GroupEntity record);
}
