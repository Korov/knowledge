package com.korov.springboot.service.entityfullservice.impl;

import com.korov.springboot.entity.MenuEntity;
import com.korov.springboot.entity.RoleEntity;
import com.korov.springboot.entity.entityfull.Role;
import com.korov.springboot.service.IAssistService;
import com.korov.springboot.service.IMenuService;
import com.korov.springboot.service.IRoleService;
import com.korov.springboot.service.entityfullservice.IRoleFullService;
import com.korov.springboot.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleFullService implements IRoleFullService {

    @Autowired
    private IAssistService assistService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Override
    public Role getRole(Integer roleId) {

        RoleEntity roleEntity = roleService.selectByPrimaryKey(roleId);

        List<Map<String,Integer>> roleMenus = assistService.selectRoleMenuByRoleId(roleId);

        List<MenuEntity> menuEntities = new ArrayList<>();

        for(Map<String,Integer> map:roleMenus){
            menuEntities.add(menuService.selectByPrimaryKey(map.get(Constant.MENU_ID)));
        }

        Role role = new Role();
        role.setRoleEntity(roleEntity);
        role.setMenuEntities(menuEntities);

        return null;
    }
}
