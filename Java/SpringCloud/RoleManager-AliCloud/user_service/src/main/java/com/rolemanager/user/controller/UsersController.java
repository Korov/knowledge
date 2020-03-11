package com.rolemanager.user.controller;

import com.rolemanager.commons.constant.Constant;
import com.rolemanager.commons.model.users.RoleModel;
import com.rolemanager.commons.model.users.UserModel;
import com.rolemanager.commons.vo.PageVo;
import com.rolemanager.commons.vo.ResultVo;
import com.rolemanager.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "获取用户信息", notes = "分页获取用户信息，并且可以传递查询信息")
    @GetMapping(value = "/users")
    public ResultVo getUsers(@PathParam(value = "query") String query,
                             @PathParam(value = "pagenum") String pagenum, @PathParam(value = "pagesize") String pagesize) {
        ResultVo<PageVo<UserModel>> resultVo = new ResultVo<>();
        resultVo.setCode(Constant.OPERATION_SUCCESS);
        resultVo.setDescription(Constant.DESCRIPTION_SUCCESS);

        PageVo<UserModel> pageVo = new PageVo<>();
        pageVo.setTotal((int) userService.getUserNum());
        pageVo.setPageNum(Integer.parseInt(pagenum));
        pageVo.setPageSize(Integer.parseInt(pagesize));
        List<UserModel> userModels = userService.getUsers(Integer.parseInt(pagenum), Integer.parseInt(pagesize));
        pageVo.setContets(userModels);
        resultVo.setData(pageVo);

        List<RoleModel> roles = new ArrayList<>();
        RoleModel role = new RoleModel();
        role.setId(1L);
        role.setRoleCode("001");
        role.setRoleName("角色1");
        roles.add(role);
        role = new RoleModel();
        role.setId(2L);
        role.setRoleCode("002");
        role.setRoleName("角色2");
        roles.add(role);
        role = new RoleModel();
        role.setId(3L);
        role.setRoleCode("003");
        role.setRoleName("角色3");
        roles.add(role);

        List<RoleModel> subRoles = new ArrayList<>();
        RoleModel subRole = new RoleModel();
        subRole.setId(11L);
        subRole.setRoleCode("011");
        subRole.setRoleName("角色11");
        subRoles.add(subRole);
        role.setRoles(subRoles);

        List<RoleModel> subRoles11 = new ArrayList<>();
        RoleModel subRole11 = new RoleModel();
        subRole11.setId(111L);
        subRole11.setRoleCode("0111");
        subRole11.setRoleName("角色111");
        subRoles11.add(subRole11);
        subRole.setRoles(subRoles11);

        userModels.forEach(user -> user.setRoles(roles));

        return resultVo;
    }

    @PostMapping(value = "/adduser")
    public ResultVo addUser(@RequestBody UserModel userModel) {
        String password = userModel.getPwd();
        userModel.setPwd(passwordEncoder.encode(password));
        if (userModel.getId() == null) {
            userModel.setCreatetime(new Date());
            userModel.setUpdatetime(new Date());
            userService.insert(userModel);
        } else {
            userService.updateByPrimaryKeySelective(userModel);
        }
        return ResultVo.getSuccess();
    }

    @DeleteMapping(value = "/deleteuser")
    public ResultVo deleteUser(@PathParam(value = "id") String id) {
        int value = userService.deleteByPrimaryKey(Long.parseLong(id));
        return ResultVo.getSuccess();
    }
}