package com.korov.springboot.service.impl;

import com.korov.springboot.dao.IPositionDao;
import com.korov.springboot.entity.PositionEntity;
import com.korov.springboot.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService implements IPositionService {

    @Autowired
    private IPositionDao positionDao;

    @Override
    public int deleteByPrimaryKey(Integer postId) {
        return positionDao.deleteByPrimaryKey(postId);
    }

    @Override
    public int insert(PositionEntity record) {
        return positionDao.insert(record);
    }

    @Override
    public int insertAll(List<PositionEntity> records) {
        return positionDao.insertAll(records);
    }

    @Override
    public PositionEntity selectByPrimaryKey(Integer postId) {
        return positionDao.selectByPrimaryKey(postId);
    }

    @Override
    public List<PositionEntity> selectAll() {
        return positionDao.selectAll();
    }

    @Override
    public List<PositionEntity> selectPage(Integer pageNum, Integer pageSize) {
        return positionDao.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(PositionEntity record) {
        return positionDao.updateByPrimaryKey(record);
    }
}
