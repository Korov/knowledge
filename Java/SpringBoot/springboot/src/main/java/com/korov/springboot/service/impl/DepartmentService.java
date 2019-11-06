package com.korov.springboot.service.impl;

import com.korov.springboot.dao.IDepartmentDao;
import com.korov.springboot.entity.DepartmentEntity;
import com.korov.springboot.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private IDepartmentDao departmentDao;

    @Override
    public int deleteByPrimaryKey(Integer deptId) {
        return departmentDao.deleteByPrimaryKey(deptId);
    }

    @Override
    public int insert(DepartmentEntity record) {
        return departmentDao.insert(record);
    }

    @Override
    public int insertAll(List<DepartmentEntity> records) {
        return departmentDao.insertAll(records);
    }

    @Override
    public DepartmentEntity selectByPrimaryKey(Integer deptId) {
        return departmentDao.selectByPrimaryKey(deptId);
    }

    @Override
    public List<DepartmentEntity> selectAll() {
        return departmentDao.selectAll();
    }

    @Override
    public List<DepartmentEntity> selectPage(Integer pageNum, Integer pageSize) {
        return departmentDao.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(DepartmentEntity record) {
        return departmentDao.updateByPrimaryKey(record);
    }
}
