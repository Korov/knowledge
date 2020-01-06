package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.IMenuDao;
import com.korov.springboot.entity.MenuEntity;
import com.korov.springboot.mapper.master.IMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDao implements IMenuDao {

    @Autowired
    private IMenuMapper menuMapper;

    @Override
    public int deleteByPrimaryKey(Integer menuId) {
        return menuMapper.deleteByPrimaryKey(menuId);
    }

    @Override
    public int insert(MenuEntity record) {
        return menuMapper.insert(record);
    }

    @Override
    public int insertAll(List<MenuEntity> records) {
        return menuMapper.insertAll(records);
    }

    @Override
    public MenuEntity selectByPrimaryKey(Integer menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public List<MenuEntity> selectAll() {
        return menuMapper.selectAll();
    }

    @Override
    public List<MenuEntity> selectPage(Integer pageNum, Integer pageSize) {
        return menuMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(MenuEntity record) {
        return menuMapper.updateByPrimaryKey(record);
    }
}
