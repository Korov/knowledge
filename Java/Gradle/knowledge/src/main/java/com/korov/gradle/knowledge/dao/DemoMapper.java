package com.korov.gradle.knowledge.dao;

import com.korov.gradle.knowledge.model.Demo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper {
    int insert(Demo record);

    List<Demo> selectAll();
}
