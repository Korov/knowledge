package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.IGroupDao;
import com.korov.springboot.entity.GroupEntity;
import com.korov.springboot.mapper.IGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDao implements IGroupDao {

    @Autowired
    private IGroupMapper groupMapper;

    @Override
    public int deleteByPrimaryKey(Integer groupId) {
        return groupMapper.deleteByPrimaryKey(groupId);
    }

    @Override
    public int insert(GroupEntity record) {
        return groupMapper.insert(record);
    }

    @Override
    public int insertAll(List<GroupEntity> records) {
        return groupMapper.insertAll(records);
    }

    @Override
    public GroupEntity selectByPrimaryKey(Integer groupId) {
        return groupMapper.selectByPrimaryKey(groupId);
    }

    @Override
    public List<GroupEntity> selectAll() {
        return groupMapper.selectAll();
    }

    @Override
    public List<GroupEntity> selectPage(Integer pageNum, Integer pageSize) {
        return groupMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(GroupEntity record) {
        return groupMapper.updateByPrimaryKey(record);
    }
}
