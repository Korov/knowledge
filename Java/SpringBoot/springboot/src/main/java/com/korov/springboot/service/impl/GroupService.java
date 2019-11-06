package com.korov.springboot.service.impl;

import com.korov.springboot.dao.IGroupDao;
import com.korov.springboot.entity.GroupEntity;
import com.korov.springboot.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService implements IGroupService {

    @Autowired
    private IGroupDao groupDao;

    @Override
    public int deleteByPrimaryKey(Integer groupId) {
        return groupDao.deleteByPrimaryKey(groupId);
    }

    @Override
    public int insert(GroupEntity record) {
        return groupDao.insert(record);
    }

    @Override
    public int insertAll(List<GroupEntity> records) {
        return groupDao.insertAll(records);
    }

    @Override
    public GroupEntity selectByPrimaryKey(Integer groupId) {
        return groupDao.selectByPrimaryKey(groupId);
    }

    @Override
    public List<GroupEntity> selectAll() {
        return groupDao.selectAll();
    }

    @Override
    public List<GroupEntity> selectPage(Integer pageNum, Integer pageSize) {
        return groupDao.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(GroupEntity record) {
        return groupDao.updateByPrimaryKey(record);
    }
}
