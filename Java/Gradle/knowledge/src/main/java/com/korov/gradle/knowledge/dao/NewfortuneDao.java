package com.korov.gradle.knowledge.dao;

import com.korov.gradle.knowledge.model.Newfortune;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewfortuneDao {
    int deleteByPrimaryKey(String pranUuid);

    int insert(Newfortune record);

    Newfortune selectByPrimaryKey(String pranUuid);

    List<Newfortune> selectAll();

    int updateByPrimaryKey(Newfortune record);

    int updateBatch(List<Newfortune> list);

    int batchInsert(@Param("list") List<Newfortune> list);
}
