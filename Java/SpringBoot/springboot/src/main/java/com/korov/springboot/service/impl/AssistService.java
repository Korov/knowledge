package com.korov.springboot.service.impl;

import com.korov.springboot.dao.IAssistDao;
import com.korov.springboot.service.IAssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AssistService implements IAssistService {

    @Autowired
    private IAssistDao assistDao;

    @Override
    public int insertUserRole(Integer userId, Integer roleId) {
        return assistDao.insertUserRole(userId, roleId);
    }

    @Override
    public int deleteUserRole(Integer userId, Integer roleId) {
        return assistDao.deleteUserRole(userId, roleId);
    }

    @Override
    public int deleteUserRoleByUserId(Integer userId) {
        return assistDao.deleteUserRoleByUserId(userId);
    }

    @Override
    public int deleteUserRoleByRoleId(Integer roleId) {
        return assistDao.deleteUserRoleByRoleId(roleId);
    }

    @Override
    public int updateUserRole(Integer oldUserId, Integer oldRoleId, Integer newUserId, Integer newRoleId) {
        return assistDao.updateUserRole(oldUserId, oldRoleId, newUserId, newRoleId);
    }

    @Override
    public List<Map<String, Integer>> selectUserRole(Integer userId, Integer roleId) {
        return assistDao.selectUserRole(userId, roleId);
    }

    @Override
    public List<Map<String, Integer>> selectUserRoleByUserId(Integer userId) {
        return assistDao.selectUserRoleByUserId(userId);
    }

    @Override
    public List<Map<String, Integer>> selectUserRoleByRoleId(Integer roleId) {
        return assistDao.selectUserRoleByRoleId(roleId);
    }

    @Override
    public int insertUserGroup(Integer userId, Integer groupId) {
        return assistDao.insertUserGroup(userId, groupId);
    }

    @Override
    public int deleteUserGroup(Integer userId, Integer groupId) {
        return assistDao.deleteUserGroup(userId, groupId);
    }

    @Override
    public int deleteUserGroupByUserId(Integer userId) {
        return assistDao.deleteUserRoleByRoleId(userId);
    }

    @Override
    public int deleteUserGroupByGroupId(Integer groupId) {
        return assistDao.deleteUserGroupByGroupId(groupId);
    }

    @Override
    public int updateUserGroup(Integer oldUserId, Integer oldGroupId, Integer newUserId, Integer newGroupId) {
        return assistDao.updateUserGroup(oldUserId, oldGroupId, newUserId, newGroupId);
    }

    @Override
    public List<Map<String, Integer>> selectUserGroup(Integer userId, Integer groupId) {
        return assistDao.selectUserGroup(userId, groupId);
    }

    @Override
    public List<Map<String, Integer>> selectUserGroupByUserId(Integer userId) {
        return assistDao.selectUserGroupByUserId(userId);
    }

    @Override
    public List<Map<String, Integer>> selectUserGroupByGroupId(Integer groupId) {
        return assistDao.selectUserGroupByGroupId(groupId);
    }

    @Override
    public int insertGroupRole(Integer groupId, Integer roleId) {
        return assistDao.insertGroupRole(groupId, roleId);
    }

    @Override
    public int deleteGroupRole(Integer groupId, Integer roleId) {
        return assistDao.deleteGroupRole(groupId, roleId);
    }

    @Override
    public int deleteGroupRoleByGroupId(Integer groupId) {
        return assistDao.deleteGroupRoleByGroupId(groupId);
    }

    @Override
    public int deleteGroupRoleByRoleId(Integer roleId) {
        return assistDao.deleteGroupRoleByRoleId(roleId);
    }

    @Override
    public int updateGroupRole(Integer oldGroupId, Integer oldRoleId, Integer newGroupId, Integer newRoleId) {
        return assistDao.updateGroupRole(oldGroupId, oldRoleId, newGroupId, newRoleId);
    }

    @Override
    public List<Map<String, Integer>> selectGroupRole(Integer groupId, Integer roleId) {
        return assistDao.selectGroupRole(groupId, roleId);
    }

    @Override
    public List<Map<String, Integer>> selectGroupRoleByGroupId(Integer groupId) {
        return assistDao.selectGroupRoleByGroupId(groupId);
    }

    @Override
    public List<Map<String, Integer>> selectGroupRoleByRoleId(Integer roleId) {
        return assistDao.selectGroupRoleByRoleId(roleId);
    }

    @Override
    public int insertRoleMenu(Integer roleId, Integer menuId) {
        return assistDao.insertRoleMenu(roleId, menuId);
    }

    @Override
    public int deleteRoleMenu(Integer roleId, Integer menuId) {
        return assistDao.deleteRoleMenu(roleId, menuId);
    }

    @Override
    public int deleteRoleMenuByRoleId(Integer roleId) {
        return assistDao.deleteRoleMenuByRoleId(roleId);
    }

    @Override
    public int deleteRoleMenuByMenuId(Integer menuId) {
        return assistDao.deleteRoleMenuByMenuId(menuId);
    }

    @Override
    public int updateRoleMenu(Integer oldRoleId, Integer oldMenuId, Integer newRoleId, Integer newMenuId) {
        return assistDao.updateRoleMenu(oldRoleId, oldMenuId, newRoleId, newMenuId);
    }

    @Override
    public List<Map<String, Integer>> selectRoleMenu(Integer roleId, Integer menuId) {
        return assistDao.selectRoleMenu(roleId, menuId);
    }

    @Override
    public List<Map<String, Integer>> selectRoleMenuByRoleId(Integer roleId) {
        return assistDao.selectRoleMenuByRoleId(roleId);
    }

    @Override
    public List<Map<String, Integer>> selectRoleMenuByMenuId(Integer menuId) {
        return assistDao.selectRoleMenuByMenuId(menuId);
    }

    @Override
    public int insertUserPosition(Integer userId, Integer postId) {
        return assistDao.insertUserPosition(userId, postId);
    }

    @Override
    public int deleteUserPosition(Integer userId, Integer postId) {
        return assistDao.deleteUserPosition(userId, postId);
    }

    @Override
    public int deleteUserPositionByUserId(Integer userId) {
        return assistDao.deleteUserPositionByUserId(userId);
    }

    @Override
    public int deleteUserPositionByPostId(Integer postId) {
        return assistDao.deleteUserPositionByPostId(postId);
    }

    @Override
    public int updateUserPosition(Integer oldUserId, Integer oldPostId, Integer newUserId, Integer newPostId) {
        return assistDao.updateUserPosition(oldUserId, oldPostId, newUserId, newPostId);
    }

    @Override
    public List<Map<String, Integer>> selectUserPosition(Integer userId, Integer postId) {
        return assistDao.selectUserPosition(userId, postId);
    }

    @Override
    public List<Map<String, Integer>> selectUserPositionByUserId(Integer userId) {
        return assistDao.selectUserPositionByUserId(userId);
    }

    @Override
    public List<Map<String, Integer>> selectUserPositionByPostId(Integer postId) {
        return assistDao.selectUserPositionByPostId(postId);
    }

    @Override
    public int insertDepartmentPosition(Integer deptId, Integer postId) {
        return assistDao.insertDepartmentPosition(deptId, postId);
    }

    @Override
    public int deleteDepartmentPosition(Integer deptId, Integer postId) {
        return assistDao.deleteDepartmentPosition(deptId, postId);
    }

    @Override
    public int deleteDepartmentPositionByDeptId(Integer deptId) {
        return assistDao.deleteDepartmentPositionByDeptId(deptId);
    }

    @Override
    public int deleteDepartmentPositionByPostId(Integer postId) {
        return assistDao.deleteDepartmentPositionByPostId(postId);
    }

    @Override
    public int updateDepartmentPosition(Integer oldDeptId, Integer oldPostId, Integer newDeptId, Integer newPostId) {
        return assistDao.updateDepartmentPosition(oldDeptId, oldPostId, newDeptId, newPostId);
    }

    @Override
    public List<Map<String, Integer>> selectDepartmentPosition(Integer deptId, Integer postId) {
        return assistDao.selectDepartmentPosition(deptId, postId);
    }

    @Override
    public List<Map<String, Integer>> selectDepartmentPositionByDeptId(Integer deptId) {
        return assistDao.selectDepartmentPositionByDeptId(deptId);
    }

    @Override
    public List<Map<String, Integer>> selectDepartmentPositionByPostId(Integer postId) {
        return assistDao.selectDepartmentPositionByPostId(postId);
    }
}
