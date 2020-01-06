package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.IDepartmentDao;
import com.korov.springboot.entity.DepartmentEntity;
import com.korov.springboot.mapper.master.IDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDao implements IDepartmentDao {

    @Autowired
    private IDepartmentMapper departmentMapper;

    @Override
    public int deleteByPrimaryKey(Integer deptId) {
        return departmentMapper.deleteByPrimaryKey(deptId);
    }

    @Override
    public int insert(DepartmentEntity record) {
        return departmentMapper.insert(record);
    }

    @Override
    public int insertAll(List<DepartmentEntity> records) {
        return departmentMapper.insertAll(records);
    }

    @Override
    public DepartmentEntity selectByPrimaryKey(Integer deptId) {
        return departmentMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public List<DepartmentEntity> selectAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public List<DepartmentEntity> selectPage(Integer pageNum, Integer pageSize) {
        return departmentMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(DepartmentEntity record) {
        return departmentMapper.updateByPrimaryKey(record);
    }
}
