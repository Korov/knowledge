package com.korov.gradle.knowledge.dao;

import com.korov.gradle.knowledge.model.DemoBak;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoBakMapper {
    int insert(DemoBak record);
}
