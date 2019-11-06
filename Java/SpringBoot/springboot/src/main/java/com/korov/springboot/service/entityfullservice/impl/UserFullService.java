package com.korov.springboot.service.entityfullservice.impl;

import com.korov.springboot.entity.GroupEntity;
import com.korov.springboot.entity.PositionEntity;
import com.korov.springboot.entity.RoleEntity;
import com.korov.springboot.entity.UserEntity;
import com.korov.springboot.entity.entityfull.SysUser;
import com.korov.springboot.service.*;
import com.korov.springboot.service.entityfullservice.IUserFullSevice;
import com.korov.springboot.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserFullService implements IUserFullSevice {

    @Autowired
    private IAssistService assistService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    private IUserService userService;

    @Override
    public SysUser getUser(Integer userId) {
        UserEntity userEntity = userService.selectByPrimaryKey(userId);

        List<RoleEntity> roleEntities = new ArrayList<>();
        List<GroupEntity> groupEntities = new ArrayList<>();
        List<PositionEntity> positionEntities = new ArrayList<>();

        getReleation(roleEntities, groupEntities, positionEntities, userId);

        return getUserFull(userEntity,roleEntities,groupEntities,positionEntities);
    }

    @Override
    public SysUser selectByUsername(String username) {
        UserEntity userEntity = userService.selectByUsername(username);


        List<RoleEntity> roleEntities = new ArrayList<>();
        List<GroupEntity> groupEntities = new ArrayList<>();
        List<PositionEntity> positionEntities = new ArrayList<>();
        Integer userId = userEntity.getUserId();

        getReleation(roleEntities, groupEntities, positionEntities, userId);

        return getUserFull(userEntity,roleEntities,groupEntities,positionEntities);
    }

    private void getReleation(List<RoleEntity> roleEntities, List<GroupEntity> groupEntities,
                              List<PositionEntity> positionEntities, Integer userId) {

        List<Map<String, Integer>> userRoles = assistService.selectUserRoleByUserId(userId);
        for (Map<String, Integer> map : userRoles) {
            roleEntities.add(roleService.selectByPrimaryKey(map.get(Constant.ROLE_ID)));
        }

        List<Map<String, Integer>> userGroups = assistService.selectUserGroupByUserId(userId);
        for (Map<String, Integer> map : userGroups) {
            groupEntities.add(groupService.selectByPrimaryKey(map.get(Constant.GROUP_ID)));
        }

        List<Map<String, Integer>> userPositions = assistService.selectUserPositionByUserId(userId);
        for (Map<String, Integer> map : userPositions) {
            positionEntities.add(positionService.selectByPrimaryKey(map.get(Constant.POST_ID)));
        }
    }

    private SysUser getUserFull(UserEntity userEntity, List<RoleEntity> roleEntities, List<GroupEntity> groupEntities,
                                List<PositionEntity> positionEntities) {

        SysUser user = new SysUser();
        user.setUserEntity(userEntity);
        user.setRoleEntities(roleEntities);
        user.setGroupEntities(groupEntities);

        return user;
    }
}
