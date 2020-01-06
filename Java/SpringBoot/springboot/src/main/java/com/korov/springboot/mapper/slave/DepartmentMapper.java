package com.korov.springboot.mapper.slave;

import com.korov.springboot.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(DepartmentEntity record);

    int insertAll(List<DepartmentEntity> records);

    DepartmentEntity selectByPrimaryKey(Integer deptId);

    List<DepartmentEntity> selectAll();

    List<DepartmentEntity> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    int updateByPrimaryKey(DepartmentEntity record);
}
