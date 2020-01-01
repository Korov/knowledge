package com.korov.springboot.dao;

import java.util.List;
import java.util.Map;

public interface IAssistDao {
    // 用户角色关系表
    int insertUserRole(Integer userId, Integer roleId);

    int deleteUserRole(Integer userId, Integer roleId);

    int deleteUserRoleByUserId(Integer userId);

    int deleteUserRoleByRoleId(Integer roleId);

    int updateUserRole(Integer oldUserId, Integer oldRoleId,
                       Integer newUserId, Integer newRoleId);

    List<Map<String, Integer>> selectUserRole(Integer userId, Integer roleId);

    List<Map<String, Integer>> selectUserRoleByUserId(Integer userId);

    List<Map<String, Integer>> selectUserRoleByRoleId(Integer roleId);

    // 用户用户组关系表
    int insertUserGroup(Integer userId, Integer groupId);

    int deleteUserGroup(Integer userId, Integer groupId);

    int deleteUserGroupByUserId(Integer userId);

    int deleteUserGroupByGroupId(Integer groupId);

    int updateUserGroup(Integer oldUserId, Integer oldGroupId,
                        Integer newUserId, Integer newGroupId);

    List<Map<String, Integer>> selectUserGroup(Integer userId, Integer groupId);

    List<Map<String, Integer>> selectUserGroupByUserId(Integer userId);

    List<Map<String, Integer>> selectUserGroupByGroupId(Integer groupId);

    // 用户组角色关系表
    int insertGroupRole(Integer groupId, Integer roleId);

    int deleteGroupRole(Integer groupId, Integer roleId);

    int deleteGroupRoleByGroupId(Integer groupId);

    int deleteGroupRoleByRoleId(Integer roleId);

    int updateGroupRole(Integer oldGroupId, Integer oldRoleId,
                        Integer newGroupId, Integer newRoleId);

    List<Map<String, Integer>> selectGroupRole(Integer groupId, Integer roleId);

    List<Map<String, Integer>> selectGroupRoleByGroupId(Integer groupId);

    List<Map<String, Integer>> selectGroupRoleByRoleId(Integer roleId);


    // 角色菜单关系表
    int insertRoleMenu(Integer roleId, Integer menuId);

    int deleteRoleMenu(Integer roleId, Integer menuId);

    int deleteRoleMenuByRoleId(Integer roleId);

    int deleteRoleMenuByMenuId(Integer menuId);

    int updateRoleMenu(Integer oldRoleId, Integer oldMenuId,
                       Integer newRoleId, Integer newMenuId);

    List<Map<String, Integer>> selectRoleMenu(Integer roleId, Integer menuId);

    List<Map<String, Integer>> selectRoleMenuByRoleId(Integer roleId);

    List<Map<String, Integer>> selectRoleMenuByMenuId(Integer menuId);

    // 用户职位关系表
    int insertUserPosition(Integer userId, Integer postId);

    int deleteUserPosition(Integer userId, Integer postId);

    int deleteUserPositionByUserId(Integer userId);

    int deleteUserPositionByPostId(Integer postId);

    int updateUserPosition(Integer oldUserId, Integer oldPostId,
                           Integer newUserId, Integer newPostId);

    List<Map<String, Integer>> selectUserPosition(Integer userId, Integer postId);

    List<Map<String, Integer>> selectUserPositionByUserId(Integer userId);

    List<Map<String, Integer>> selectUserPositionByPostId(Integer postId);

    // 职位部门关系表
    int insertDepartmentPosition(Integer deptId, Integer postId);

    int deleteDepartmentPosition(Integer deptId, Integer postId);

    int deleteDepartmentPositionByDeptId(Integer deptId);

    int deleteDepartmentPositionByPostId(Integer postId);

    int updateDepartmentPosition(Integer oldDeptId, Integer oldPostId,
                                 Integer newDeptId, Integer newPostId);

    List<Map<String, Integer>> selectDepartmentPosition(Integer deptId, Integer postId);

    List<Map<String, Integer>> selectDepartmentPositionByDeptId(Integer deptId);

    List<Map<String, Integer>> selectDepartmentPositionByPostId(Integer postId);
}
