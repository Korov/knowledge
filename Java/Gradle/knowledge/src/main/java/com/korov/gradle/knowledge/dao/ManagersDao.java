package com.korov.gradle.knowledge.dao;

import com.korov.gradle.knowledge.model.Managers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagersDao {
    int insert(Managers record);

    List<Managers> selectAll();

    int insertAllResult(List<Managers> list);
}
