package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.IPositionDao;
import com.korov.springboot.entity.PositionEntity;
import com.korov.springboot.mapper.IPositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PositionDao implements IPositionDao {

    @Autowired
    private IPositionMapper positionMapper;

    @Override
    public int deleteByPrimaryKey(Integer postId) {
        return positionMapper.deleteByPrimaryKey(postId);
    }

    @Override
    public int insert(PositionEntity record) {
        return positionMapper.insert(record);
    }

    @Override
    public int insertAll(List<PositionEntity> records) {
        return positionMapper.insertAll(records);
    }

    @Override
    public PositionEntity selectByPrimaryKey(Integer postId) {
        return positionMapper.selectByPrimaryKey(postId);
    }

    @Override
    public List<PositionEntity> selectAll() {
        return positionMapper.selectAll();
    }

    @Override
    public List<PositionEntity> selectPage(Integer pageNum, Integer pageSize) {
        return positionMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(PositionEntity record) {
        return positionMapper.updateByPrimaryKey(record);
    }
}
