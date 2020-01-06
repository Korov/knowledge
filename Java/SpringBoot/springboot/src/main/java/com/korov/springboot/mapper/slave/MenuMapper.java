package com.korov.springboot.mapper.slave;

import com.korov.springboot.entity.MenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(MenuEntity record);

    int insertAll(List<MenuEntity> records);

    MenuEntity selectByPrimaryKey(Integer menuId);

    List<MenuEntity> selectAll();

    List<MenuEntity> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    int updateByPrimaryKey(MenuEntity record);
}
