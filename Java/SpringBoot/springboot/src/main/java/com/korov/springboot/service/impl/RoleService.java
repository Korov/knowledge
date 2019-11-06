package com.korov.springboot.service.impl;

import com.korov.springboot.dao.IRoleDao;
import com.korov.springboot.entity.RoleEntity;
import com.korov.springboot.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public int deleteByPrimaryKey(Integer roleId) {
        return roleDao.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(RoleEntity record) {
        return roleDao.insert(record);
    }

    @Override
    public int insertAll(List<RoleEntity> records) {
        return roleDao.insertAll(records);
    }

    @Override
    public RoleEntity selectByPrimaryKey(Integer roleId) {
        return roleDao.selectByPrimaryKey(roleId);
    }

    @Override
    public List<RoleEntity> selectAll() {
        return roleDao.selectAll();
    }

    @Override
    public List<RoleEntity> selectPage(Integer pageNum, Integer pageSize) {
        return roleDao.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(RoleEntity record) {
        return roleDao.updateByPrimaryKey(record);
    }
}
