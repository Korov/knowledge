package com.distributed.lock.mysql.dao;

import com.distributed.lock.mysql.model.TableLockMethod;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableLockMethodDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TableLockMethod record);

    int insertSelective(TableLockMethod record);

    TableLockMethod selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TableLockMethod record);

    int updateByPrimaryKey(TableLockMethod record);

    TableLockMethod selectByResources(String resources);
}