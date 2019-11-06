package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.IRoleDao;
import com.korov.springboot.entity.RoleEntity;
import com.korov.springboot.mapper.IRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao implements IRoleDao {

    @Autowired
    private IRoleMapper roleMapper;


    @Override
    public int deleteByPrimaryKey(Integer roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(RoleEntity record) {
        return roleMapper.insert(record);
    }

    @Override
    public int insertAll(List<RoleEntity> records) {
        return roleMapper.insertAll(records);
    }

    @Override
    public RoleEntity selectByPrimaryKey(Integer userId) {
        return roleMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<RoleEntity> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public List<RoleEntity> selectPage(Integer pageNum, Integer pageSize) {
        return roleMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(RoleEntity record) {
        return roleMapper.updateByPrimaryKey(record);
    }
}
