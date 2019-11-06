package com.korov.springboot.dao;

import com.korov.springboot.entity.DepartmentEntity;

import java.util.List;

public interface IDepartmentDao {
    int deleteByPrimaryKey(Integer deptId);

    int insert(DepartmentEntity record);

    int insertAll(List<DepartmentEntity> records);

    DepartmentEntity selectByPrimaryKey(Integer deptId);

    List<DepartmentEntity> selectAll();

    List<DepartmentEntity> selectPage(Integer pageNum, Integer pageSize);

    int updateByPrimaryKey(DepartmentEntity record);
}
