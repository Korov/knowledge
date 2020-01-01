package com.korov.springboot.mapper;

import com.korov.springboot.entity.PositionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPositionMapper {
    int deleteByPrimaryKey(Integer postId);

    int insert(PositionEntity record);

    int insertAll(List<PositionEntity> records);

    PositionEntity selectByPrimaryKey(Integer postId);

    List<PositionEntity> selectAll();

    List<PositionEntity> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    int updateByPrimaryKey(PositionEntity record);
}
