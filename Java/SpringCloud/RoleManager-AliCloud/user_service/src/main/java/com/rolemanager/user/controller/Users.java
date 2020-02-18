package com.rolemanager.user.controller;

import com.rolemanager.user.constant.Constant;
import com.rolemanager.user.model.UserModel;
import com.rolemanager.user.service.UserService;
import com.rolemanager.user.vo.PageVo;
import com.rolemanager.user.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class Users {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResultVo getUsers(@PathParam(value = "query") String query,
                             @PathParam(value = "pagenum") String pagenum, @PathParam(value = "pagesize") String pagesize) {
        ResultVo<PageVo<UserModel>> resultVo = new ResultVo<>();
        resultVo.setCode(Constant.OPERATION_SUCCESS);
        resultVo.setDescription(Constant.DESCRIPTION_SUCCESS);


        List<PageVo<UserModel>> users = new ArrayList<>();
        PageVo<UserModel> pageVo = new PageVo<>();
        pageVo.setTotal((int) userService.getUserNum());
        pageVo.setPageNum(Integer.parseInt(pagenum));
        pageVo.setPageSize(Integer.parseInt(pagesize));
        List<UserModel> userModels = userService.getUsers(Integer.parseInt(pagenum), Integer.parseInt(pagesize));
        pageVo.setContets(userModels);
        users.add(pageVo);
        resultVo.setData(users);
        return resultVo;
    }

    @PostMapping(value = "/adduser")
    public ResultVo addUser(@RequestBody UserModel userModel) {
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
