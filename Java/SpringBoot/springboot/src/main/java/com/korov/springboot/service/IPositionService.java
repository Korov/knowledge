package com.korov.springboot.service;

import com.korov.springboot.entity.PositionEntity;

import java.util.List;

public interface IPositionService {
    int deleteByPrimaryKey(Integer postId);

    int insert(PositionEntity record);

    int insertAll(List<PositionEntity> records);

    PositionEntity selectByPrimaryKey(Integer postId);

    List<PositionEntity> selectAll();

    List<PositionEntity> selectPage(Integer pageNum, Integer pageSize);

    int updateByPrimaryKey(PositionEntity record);
}
