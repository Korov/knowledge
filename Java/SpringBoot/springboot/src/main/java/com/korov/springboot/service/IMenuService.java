package com.korov.springboot.service;

import com.korov.springboot.entity.MenuEntity;

import java.util.List;

public interface IMenuService {
    int deleteByPrimaryKey(Integer menuId);

    int insert(MenuEntity record);

    int insertAll(List<MenuEntity> records);

    MenuEntity selectByPrimaryKey(Integer menuId);

    List<MenuEntity> selectAll();

    List<MenuEntity> selectPage(Integer pageNum, Integer pageSize);

    int updateByPrimaryKey(MenuEntity record);
}
