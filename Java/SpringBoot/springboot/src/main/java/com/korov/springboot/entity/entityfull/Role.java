package com.korov.springboot.entity.entityfull;

import com.korov.springboot.entity.MenuEntity;
import com.korov.springboot.entity.RoleEntity;

import java.util.List;

public class Role {
    private RoleEntity roleEntity;
    private List<MenuEntity> menuEntities;

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    public List<MenuEntity> getMenuEntities() {
        return menuEntities;
    }

    public void setMenuEntities(List<MenuEntity> menuEntities) {
        this.menuEntities = menuEntities;
    }
}
