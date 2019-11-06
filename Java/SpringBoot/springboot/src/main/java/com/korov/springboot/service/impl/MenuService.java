package com.korov.springboot.service.impl;

import com.korov.springboot.dao.IMenuDao;
import com.korov.springboot.entity.MenuEntity;
import com.korov.springboot.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private IMenuDao menuDao;

    @Override
    public int deleteByPrimaryKey(Integer menuId) {
        return menuDao.deleteByPrimaryKey(menuId);
    }

    @Override
    public int insert(MenuEntity record) {
        return menuDao.insert(record);
    }

    @Override
    public int insertAll(List<MenuEntity> records) {
        return menuDao.insertAll(records);
    }

    @Override
    public MenuEntity selectByPrimaryKey(Integer menuId) {
        return menuDao.selectByPrimaryKey(menuId);
    }

    @Override
    public List<MenuEntity> selectAll() {
        return menuDao.selectAll();
    }

    @Override
    public List<MenuEntity> selectPage(Integer pageNum, Integer pageSize) {
        return menuDao.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(MenuEntity record) {
        return menuDao.updateByPrimaryKey(record);
    }
}
