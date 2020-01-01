package com.korov.springboot.dao.impl;

import com.korov.springboot.dao.IAssistDao;
import com.korov.springboot.mapper.IAssistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Korov
 */
@Repository
public class AssistDao implements IAssistDao {

    @Autowired
    private IAssistMapper assistMapper;

    @Override
    public int insertUserRole(Integer userId, Integer roleId) {
        return assistMapper.insertUserRole(userId, roleId);
    }

    @Override
    public int deleteUserRole(Integer userId, Integer roleId) {
        return assistMapper.deleteUserRole(userId, roleId);
    }

    @Override
    public int deleteUserRoleByUserId(Integer userId) {
        return assistMapper.deleteUserRoleByUserId(userId);
    }

    @Override
    public int deleteUserRoleByRoleId(Integer roleId) {
        return assistMapper.deleteUserRoleByRoleId(roleId);
    }

    @Override
    public int updateUserRole(Integer oldUserId, Integer oldRoleId, Integer newUserId, Integer newRoleId) {
        return assistMapper.updateUserRole(oldUserId, oldRoleId, newUserId, newRoleId);
    }

    @Override
    public List<Map<String, Integer>> selectUserRole(Integer userId, Integer roleId) {
        return assistMapper.selectUserRole(userId, roleId);
    }

    @Override
    public List<Map<String, Integer>> selectUserRoleByUserId(Integer userId) {
        return assistMapper.selectUserRoleByUserId(userId);
    }

    @Override
    public List<Map<String, Integer>> selectUserRoleByRoleId(Integer roleId) {
        return assistMapper.selectUserRoleByRoleId(roleId);
    }

    @Override
    public int insertUserGroup(Integer userId, Integer groupId) {
        return assistMapper.insertUserGroup(userId, groupId);
    }

    @Override
    public int deleteUserGroup(Integer userId, Integer groupId) {
        return assistMapper.deleteUserGroup(userId, groupId);
    }

    @Override
    public int deleteUserGroupByUserId(Integer userId) {
        return assistMapper.deleteUserRoleByRoleId(userId);
    }

    @Override
    public int deleteUserGroupByGroupId(Integer groupId) {
        return assistMapper.deleteUserGroupByGroupId(groupId);
    }

    @Override
    public int updateUserGroup(Integer oldUserId, Integer oldGroupId, Integer newUserId, Integer newGroupId) {
        return assistMapper.updateUserGroup(oldUserId, oldGroupId, newUserId, newGroupId);
    }

    @Override
    public List<Map<String, Integer>> selectUserGroup(Integer userId, Integer groupId) {
        return assistMapper.selectUserGroup(userId, groupId);
    }

    @Override
    public List<Map<String, Integer>> selectUserGroupByUserId(Integer userId) {
        return assistMapper.selectUserGroupByUserId(userId);
    }

    @Override
    public List<Map<String, Integer>> selectUserGroupByGroupId(Integer groupId) {
        return assistMapper.selectUserGroupByGroupId(groupId);
    }

    @Override
    public int insertGroupRole(Integer groupId, Integer roleId) {
        return assistMapper.insertGroupRole(groupId, roleId);
    }

    @Override
    public int deleteGroupRole(Integer groupId, Integer roleId) {
        return assistMapper.deleteGroupRole(groupId, roleId);
    }

    @Override
    public int deleteGroupRoleByGroupId(Integer groupId) {
        return assistMapper.deleteGroupRoleByGroupId(groupId);
    }

    @Override
    public int deleteGroupRoleByRoleId(Integer roleId) {
        return assistMapper.deleteGroupRoleByRoleId(roleId);
    }

    @Override
    public int updateGroupRole(Integer oldGroupId, Integer oldRoleId, Integer newGroupId, Integer newRoleId) {
        return assistMapper.updateGroupRole(oldGroupId, oldRoleId, newGroupId, newRoleId);
    }

    @Override
    public List<Map<String, Integer>> selectGroupRole(Integer groupId, Integer roleId) {
        return assistMapper.selectGroupRole(groupId, roleId);
    }

    @Override
    public List<Map<String, Integer>> selectGroupRoleByGroupId(Integer groupId) {
        return assistMapper.selectGroupRoleByGroupId(groupId);
    }

    @Override
    public List<Map<String, Integer>> selectGroupRoleByRoleId(Integer roleId) {
        return assistMapper.selectGroupRoleByRoleId(roleId);
    }

    @Override
    public int insertRoleMenu(Integer roleId, Integer menuId) {
        return assistMapper.insertRoleMenu(roleId, menuId);
    }

    @Override
    public int deleteRoleMenu(Integer roleId, Integer menuId) {
        return assistMapper.deleteRoleMenu(roleId, menuId);
    }

    @Override
    public int deleteRoleMenuByRoleId(Integer roleId) {
        return assistMapper.deleteRoleMenuByRoleId(roleId);
    }

    @Override
    public int deleteRoleMenuByMenuId(Integer menuId) {
        return assistMapper.deleteRoleMenuByMenuId(menuId);
    }

    @Override
    public int updateRoleMenu(Integer oldRoleId, Integer oldMenuId, Integer newRoleId, Integer newMenuId) {
        return assistMapper.updateRoleMenu(oldRoleId, oldMenuId, newRoleId, newMenuId);
    }

    @Override
    public List<Map<String, Integer>> selectRoleMenu(Integer roleId, Integer menuId) {
        return assistMapper.selectRoleMenu(roleId, menuId);
    }

    @Override
    public List<Map<String, Integer>> selectRoleMenuByRoleId(Integer roleId) {
        return assistMapper.selectRoleMenuByRoleId(roleId);
    }

    @Override
    public List<Map<String, Integer>> selectRoleMenuByMenuId(Integer menuId) {
        return assistMapper.selectRoleMenuByMenuId(menuId);
    }

    @Override
    public int insertUserPosition(Integer userId, Integer postId) {
        return assistMapper.insertUserPosition(userId, postId);
    }

    @Override
    public int deleteUserPosition(Integer userId, Integer postId) {
        return assistMapper.deleteUserPosition(userId, postId);
    }

    @Override
    public int deleteUserPositionByUserId(Integer userId) {
        return assistMapper.deleteUserPositionByUserId(userId);
    }

    @Override
    public int deleteUserPositionByPostId(Integer postId) {
        return assistMapper.deleteUserPositionByPostId(postId);
    }

    @Override
    public int updateUserPosition(Integer oldUserId, Integer oldPostId, Integer newUserId, Integer newPostId) {
        return assistMapper.updateUserPosition(oldUserId, oldPostId, newUserId, newPostId);
    }

    @Override
    public List<Map<String, Integer>> selectUserPosition(Integer userId, Integer postId) {
        return assistMapper.selectUserPosition(userId, postId);
    }

    @Override
    public List<Map<String, Integer>> selectUserPositionByUserId(Integer userId) {
        return assistMapper.selectUserPositionByUserId(userId);
    }

    @Override
    public List<Map<String, Integer>> selectUserPositionByPostId(Integer postId) {
        return assistMapper.selectUserPositionByPostId(postId);
    }

    @Override
    public int insertDepartmentPosition(Integer deptId, Integer postId) {
        return assistMapper.insertDepartmentPosition(deptId, postId);
    }

    @Override
    public int deleteDepartmentPosition(Integer deptId, Integer postId) {
        return assistMapper.deleteDepartmentPosition(deptId, postId);
    }

    @Override
    public int deleteDepartmentPositionByDeptId(Integer deptId) {
        return assistMapper.deleteDepartmentPositionByDeptId(deptId);
    }

    @Override
    public int deleteDepartmentPositionByPostId(Integer postId) {
        return assistMapper.deleteDepartmentPositionByPostId(postId);
    }

    @Override
    public int updateDepartmentPosition(Integer oldDeptId, Integer oldPostId, Integer newDeptId, Integer newPostId) {
        return assistMapper.updateDepartmentPosition(oldDeptId, oldPostId, newDeptId, newPostId);
    }

    @Override
    public List<Map<String, Integer>> selectDepartmentPosition(Integer deptId, Integer postId) {
        return assistMapper.selectDepartmentPosition(deptId, postId);
    }

    @Override
    public List<Map<String, Integer>> selectDepartmentPositionByDeptId(Integer deptId) {
        return assistMapper.selectDepartmentPositionByDeptId(deptId);
    }

    @Override
    public List<Map<String, Integer>> selectDepartmentPositionByPostId(Integer postId) {
        return assistMapper.selectDepartmentPositionByPostId(postId);
    }
}
