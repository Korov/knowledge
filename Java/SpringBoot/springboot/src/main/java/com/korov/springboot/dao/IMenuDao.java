package com.korov.springboot.dao;

import com.korov.springboot.entity.MenuEntity;

import java.util.List;

public interface IMenuDao {
    int deleteByPrimaryKey(Integer menuId);

    int insert(MenuEntity record);

    int insertAll(List<MenuEntity> records);

    MenuEntity selectByPrimaryKey(Integer menuId);

    List<MenuEntity> selectAll();

    List<MenuEntity> selectPage(Integer pageNum, Integer pageSize);

    int updateByPrimaryKey(MenuEntity record);
}
