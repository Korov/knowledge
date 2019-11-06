package com.korov.springboot.entity.entityfull;

import com.korov.springboot.entity.GroupEntity;
import com.korov.springboot.entity.PositionEntity;
import com.korov.springboot.entity.RoleEntity;
import com.korov.springboot.entity.UserEntity;

import java.util.List;

public class SysUser {
    private UserEntity userEntity;
    private List<GroupEntity> groupEntities;
    private List<RoleEntity> roleEntities;
    private List<PositionEntity> positionEntities;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<GroupEntity> getGroupEntities() {
        return groupEntities;
    }

    public void setGroupEntities(List<GroupEntity> groupEntities) {
        this.groupEntities = groupEntities;
    }

    public List<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(List<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    public List<PositionEntity> getPositionEntities() {
        return positionEntities;
    }

    public void setPositionEntities(List<PositionEntity> positionEntities) {
        this.positionEntities = positionEntities;
    }
}
