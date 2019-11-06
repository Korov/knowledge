package com.korov.springboot.BaseTest.MapperTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.mapper.IAssistMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class AssisMapperTest extends SpringbootApplicationTests {
    @Autowired
    private IAssistMapper iAssistMapper;

    @Test
    public void mapperTest() {
        int insertCount = 0;
        int updateCount = 0;
        int deleteCount = 0;
        List<Map<String, Integer>> list1 = null;
        List<Map<String, Integer>> list2 = null;
        List<Map<String, Integer>> list3 = null;
        insertCount += iAssistMapper.insertUserRole(1, 1);
        insertCount += iAssistMapper.insertUserRole(2, 1);
        insertCount += iAssistMapper.insertUserRole(2, 2);
        insertCount += iAssistMapper.insertUserRole(3, 3);
        insertCount += iAssistMapper.insertUserRole(4, 3);
        updateCount += iAssistMapper.updateUserRole(1, 1, 5, 5);
        updateCount += iAssistMapper.updateUserRole(5, 5, 1, 1);
        list1 = iAssistMapper.selectUserRole(1, 1);
        list2 = iAssistMapper.selectUserRoleByUserId(2);
        list3 = iAssistMapper.selectUserRoleByRoleId(3);
        deleteCount += iAssistMapper.deleteUserRole(1, 1);
        deleteCount += iAssistMapper.deleteUserRoleByUserId(2);
        deleteCount += iAssistMapper.deleteUserRoleByRoleId(3);
        System.out.printf("insert:%s\nupdate:%s\ndelete:%s\n", insertCount, updateCount, deleteCount);

        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        list1 = null;
        list2 = null;
        list3 = null;
        insertCount += iAssistMapper.insertUserGroup(1, 1);
        insertCount += iAssistMapper.insertUserGroup(2, 1);
        insertCount += iAssistMapper.insertUserGroup(2, 2);
        insertCount += iAssistMapper.insertUserGroup(3, 3);
        insertCount += iAssistMapper.insertUserGroup(4, 3);
        updateCount += iAssistMapper.updateUserGroup(1, 1, 5, 5);
        updateCount += iAssistMapper.updateUserGroup(5, 5, 1, 1);
        list1 = iAssistMapper.selectUserGroup(1, 1);
        list2 = iAssistMapper.selectUserGroupByUserId(2);
        list3 = iAssistMapper.selectUserGroupByGroupId(3);
        deleteCount += iAssistMapper.deleteUserGroup(1, 1);
        deleteCount += iAssistMapper.deleteUserGroupByUserId(2);
        deleteCount += iAssistMapper.deleteUserGroupByGroupId(3);
        System.out.printf("insert:%s\nupdate:%s\ndelete:%s\n", insertCount, updateCount, deleteCount);

        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        list1 = null;
        list2 = null;
        list3 = null;
        insertCount += iAssistMapper.insertGroupRole(1, 1);
        insertCount += iAssistMapper.insertGroupRole(2, 1);
        insertCount += iAssistMapper.insertGroupRole(2, 2);
        insertCount += iAssistMapper.insertGroupRole(3, 3);
        insertCount += iAssistMapper.insertGroupRole(4, 3);
        updateCount += iAssistMapper.updateGroupRole(1, 1, 5, 5);
        updateCount += iAssistMapper.updateGroupRole(5, 5, 1, 1);
        list1 = iAssistMapper.selectGroupRole(1, 1);
        list2 = iAssistMapper.selectGroupRoleByGroupId(2);
        list3 = iAssistMapper.selectGroupRoleByRoleId(3);
        deleteCount += iAssistMapper.deleteGroupRole(1, 1);
        deleteCount += iAssistMapper.deleteGroupRoleByGroupId(2);
        deleteCount += iAssistMapper.deleteGroupRoleByRoleId(3);
        System.out.printf("insert:%s\nupdate:%s\ndelete:%s\n", insertCount, updateCount, deleteCount);

        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        list1 = null;
        list2 = null;
        list3 = null;
        insertCount += iAssistMapper.insertRoleMenu(1, 1);
        insertCount += iAssistMapper.insertRoleMenu(2, 1);
        insertCount += iAssistMapper.insertRoleMenu(2, 2);
        insertCount += iAssistMapper.insertRoleMenu(3, 3);
        insertCount += iAssistMapper.insertRoleMenu(4, 3);
        updateCount += iAssistMapper.updateRoleMenu(1, 1, 5, 5);
        updateCount += iAssistMapper.updateRoleMenu(5, 5, 1, 1);
        list1 = iAssistMapper.selectRoleMenu(1, 1);
        list2 = iAssistMapper.selectRoleMenuByRoleId(2);
        list3 = iAssistMapper.selectRoleMenuByMenuId(3);
        deleteCount += iAssistMapper.deleteRoleMenu(1, 1);
        deleteCount += iAssistMapper.deleteRoleMenuByRoleId(2);
        deleteCount += iAssistMapper.deleteRoleMenuByMenuId(3);
        System.out.printf("insert:%s\nupdate:%s\ndelete:%s\n", insertCount, updateCount, deleteCount);

        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        list1 = null;
        list2 = null;
        list3 = null;
        insertCount += iAssistMapper.insertUserPosition(1, 1);
        insertCount += iAssistMapper.insertUserPosition(2, 1);
        insertCount += iAssistMapper.insertUserPosition(2, 2);
        insertCount += iAssistMapper.insertUserPosition(3, 3);
        insertCount += iAssistMapper.insertUserPosition(4, 3);
        updateCount += iAssistMapper.updateUserPosition(1, 1, 5, 5);
        updateCount += iAssistMapper.updateUserPosition(5, 5, 1, 1);
        list1 = iAssistMapper.selectUserPosition(1, 1);
        list2 = iAssistMapper.selectUserPositionByUserId(2);
        list3 = iAssistMapper.selectUserPositionByPostId(3);
        deleteCount += iAssistMapper.deleteUserPosition(1, 1);
        deleteCount += iAssistMapper.deleteUserPositionByUserId(2);
        deleteCount += iAssistMapper.deleteUserPositionByPostId(3);
        System.out.printf("insert:%s\nupdate:%s\ndelete:%s\n", insertCount, updateCount, deleteCount);

        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        list1 = null;
        list2 = null;
        list3 = null;
        insertCount += iAssistMapper.insertDepartmentPosition(1, 1);
        insertCount += iAssistMapper.insertDepartmentPosition(2, 1);
        insertCount += iAssistMapper.insertDepartmentPosition(2, 2);
        insertCount += iAssistMapper.insertDepartmentPosition(3, 3);
        insertCount += iAssistMapper.insertDepartmentPosition(4, 3);
        updateCount += iAssistMapper.updateDepartmentPosition(1, 1, 5, 5);
        updateCount += iAssistMapper.updateDepartmentPosition(5, 5, 1, 1);
        list1 = iAssistMapper.selectDepartmentPosition(1, 1);
        list2 = iAssistMapper.selectDepartmentPositionByDeptId(2);
        list3 = iAssistMapper.selectDepartmentPositionByPostId(3);
        deleteCount += iAssistMapper.deleteDepartmentPosition(1, 1);
        deleteCount += iAssistMapper.deleteDepartmentPositionByDeptId(2);
        deleteCount += iAssistMapper.deleteDepartmentPositionByPostId(3);
        System.out.printf("insert:%s\nupdate:%s\ndelete:%s\n", insertCount, updateCount, deleteCount);
    }
}
