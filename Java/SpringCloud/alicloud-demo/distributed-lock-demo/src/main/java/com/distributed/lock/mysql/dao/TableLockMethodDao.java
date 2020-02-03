package com.distributed.lock.mysql.dao;

import com.distributed.lock.mysql.model.TableLockMethod;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TableLockMethodDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TableLockMethod record);

    int insertSelective(TableLockMethod record);

    TableLockMethod selectByPrimaryKey(Integer id);

    TableLockMethod selectByResources(String resources);

    List<TableLockMethod> selectAllLocked();

    int updateByPrimaryKeySelective(TableLockMethod record);

    int updateByPrimaryKey(TableLockMethod record);
}