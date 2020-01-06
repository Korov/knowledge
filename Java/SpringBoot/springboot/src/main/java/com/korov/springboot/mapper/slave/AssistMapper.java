package com.korov.springboot.mapper.slave;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AssistMapper {

    // 用户角色关系表
    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int deleteUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int deleteUserRoleByUserId(@Param("userId") Integer userId);

    int deleteUserRoleByRoleId(@Param("roleId") Integer roleId);

    int updateUserRole(@Param("oldUserId") Integer oldUserId, @Param("oldRoleId") Integer oldRoleId,
                       @Param("newUserId") Integer newUserId, @Param("newRoleId") Integer newRoleId);

    List<Map<String, Integer>> selectUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    List<Map<String, Integer>> selectUserRoleByUserId(@Param("userId") Integer userId);

    List<Map<String, Integer>> selectUserRoleByRoleId(@Param("roleId") Integer roleId);

    // 用户用户组关系表
    int insertUserGroup(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    int deleteUserGroup(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    int deleteUserGroupByUserId(@Param("userId") Integer userId);

    int deleteUserGroupByGroupId(@Param("groupId") Integer groupId);

    int updateUserGroup(@Param("oldUserId") Integer oldUserId, @Param("oldGroupId") Integer oldGroupId,
                        @Param("newUserId") Integer newUserId, @Param("newGroupId") Integer newGroupId);

    List<Map<String, Integer>> selectUserGroup(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    List<Map<String, Integer>> selectUserGroupByUserId(@Param("userId") Integer userId);

    List<Map<String, Integer>> selectUserGroupByGroupId(@Param("groupId") Integer groupId);

    // 用户组角色关系表
    int insertGroupRole(@Param("groupId") Integer groupId, @Param("roleId") Integer roleId);

    int deleteGroupRole(@Param("groupId") Integer groupId, @Param("roleId") Integer roleId);

    int deleteGroupRoleByGroupId(@Param("groupId") Integer groupId);

    int deleteGroupRoleByRoleId(@Param("roleId") Integer roleId);

    int updateGroupRole(@Param("oldGroupId") Integer oldGroupId, @Param("oldRoleId") Integer oldRoleId,
                        @Param("newGroupId") Integer newGroupId, @Param("newRoleId") Integer newRoleId);

    List<Map<String, Integer>> selectGroupRole(@Param("groupId") Integer groupId, @Param("roleId") Integer roleId);

    List<Map<String, Integer>> selectGroupRoleByGroupId(@Param("groupId") Integer groupId);

    List<Map<String, Integer>> selectGroupRoleByRoleId(@Param("roleId") Integer roleId);


    // 角色菜单关系表
    int insertRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int deleteRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int deleteRoleMenuByRoleId(@Param("roleId") Integer roleId);

    int deleteRoleMenuByMenuId(@Param("menuId") Integer menuId);

    int updateRoleMenu(@Param("oldRoleId") Integer oldRoleId, @Param("oldMenuId") Integer oldMenuId,
                       @Param("newRoleId") Integer newRoleId, @Param("newMenuId") Integer newMenuId);

    List<Map<String, Integer>> selectRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    List<Map<String, Integer>> selectRoleMenuByRoleId(@Param("roleId") Integer roleId);

    List<Map<String, Integer>> selectRoleMenuByMenuId(@Param("menuId") Integer menuId);

    // 用户职位关系表
    int insertUserPosition(@Param("userId") Integer userId, @Param("postId") Integer postId);

    int deleteUserPosition(@Param("userId") Integer userId, @Param("postId") Integer postId);

    int deleteUserPositionByUserId(@Param("userId") Integer userId);

    int deleteUserPositionByPostId(@Param("postId") Integer postId);

    int updateUserPosition(@Param("oldUserId") Integer oldUserId, @Param("oldPostId") Integer oldPostId,
                           @Param("newUserId") Integer newUserId, @Param("newPostId") Integer newPostId);

    List<Map<String, Integer>> selectUserPosition(@Param("userId") Integer userId, @Param("postId") Integer postId);

    List<Map<String, Integer>> selectUserPositionByUserId(@Param("userId") Integer userId);

    List<Map<String, Integer>> selectUserPositionByPostId(@Param("postId") Integer postId);

    // 职位部门关系表
    int insertDepartmentPosition(@Param("deptId") Integer deptId, @Param("postId") Integer postId);

    int deleteDepartmentPosition(@Param("deptId") Integer deptId, @Param("postId") Integer postId);

    int deleteDepartmentPositionByDeptId(@Param("deptId") Integer deptId);

    int deleteDepartmentPositionByPostId(@Param("postId") Integer postId);

    int updateDepartmentPosition(@Param("oldDeptId") Integer oldDeptId, @Param("oldPostId") Integer oldPostId,
                                 @Param("newDeptId") Integer newDeptId, @Param("newPostId") Integer newPostId);

    List<Map<String, Integer>> selectDepartmentPosition(@Param("deptId") Integer deptId, @Param("postId") Integer postId);

    List<Map<String, Integer>> selectDepartmentPositionByDeptId(@Param("deptId") Integer deptId);

    List<Map<String, Integer>> selectDepartmentPositionByPostId(@Param("postId") Integer postId);
}
