package com.korov.springboot.mapper.master;

import com.korov.springboot.entity.RoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(RoleEntity record);

    int insertAll(List<RoleEntity> records);

    RoleEntity selectByPrimaryKey(Integer userId);

    List<RoleEntity> selectAll();

    List<RoleEntity> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    int updateByPrimaryKey(RoleEntity record);
}
