package com.distributed.transaction.dao;

import com.distributed.transaction.model.TransactionLog;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransactionLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TransactionLog record);

    int insertSelective(TransactionLog record);

    TransactionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TransactionLog record);

    int updateByPrimaryKey(TransactionLog record);

    int updateBatch(List<TransactionLog> list);

    int batchInsert(@Param("list") List<TransactionLog> list);

    List<TransactionLog> selectNewLog();

    List<TransactionLog> selectRecLog();
}