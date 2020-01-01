package com.korov.springboot.dao;

import com.korov.springboot.entity.RoleEntity;

import java.util.List;

public interface IRoleDao {
    int deleteByPrimaryKey(Integer roleId);

    int insert(RoleEntity record);

    int insertAll(List<RoleEntity> records);

    RoleEntity selectByPrimaryKey(Integer roleId);

    List<RoleEntity> selectAll();

    List<RoleEntity> selectPage(Integer pageNum, Integer pageSize);

    int updateByPrimaryKey(RoleEntity record);
}
