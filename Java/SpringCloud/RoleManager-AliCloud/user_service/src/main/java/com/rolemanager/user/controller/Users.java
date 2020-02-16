package com.rolemanager.user.controller;

import com.rolemanager.user.constant.Constant;
import com.rolemanager.user.model.UserModel;
import com.rolemanager.user.vo.PageVo;
import com.rolemanager.user.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Users {
    @GetMapping(value = "/users")
    public ResultVo getUsers(@PathParam(value = "query") String query,
                             @PathParam(value = "pagenum") String pagenum, @PathParam(value = "pagesize") String pagesize) {
        ResultVo<PageVo<UserModel>> resultVo = new ResultVo<>();
        resultVo.setCode(Constant.OPERATION_SUCCESS);
        resultVo.setDescription(Constant.DESCRIPTION_SUCCESS);

        List<PageVo<UserModel>> users = new ArrayList<>();
        PageVo<UserModel> pageVo = new PageVo<>();
        pageVo.setTotal(3);
        pageVo.setPageNum(1);
        pageVo.setPageSize(10);
        List<UserModel> userModels = new ArrayList<>();
        pageVo.setContets(userModels);

        UserModel userModel = new UserModel();
        userModel.setId(4L);
        userModel.setName("admin");
        userModel.setEmail("admin@123.com");
        userModel.setPhone("12345");
        userModel.setNickname("超级管理员");
        userModel.setStatus(0);
        userModels.add(userModel);

        userModel = new UserModel();
        userModel.setId(5L);
        userModel.setName("user1");
        userModel.setEmail("user@123.com");
        userModel.setPhone("12345");
        userModel.setNickname("用户");
        userModel.setStatus(0);
        userModels.add(userModel);

        userModel = new UserModel();
        userModel.setId(6L);
        userModel.setName("user2");
        userModel.setEmail("user2@123.com");
        userModel.setPhone("12345");
        userModel.setNickname("用户");
        userModel.setStatus(1);
        userModels.add(userModel);

        users.add(pageVo);
        resultVo.setData(users);

        return resultVo;
    }
}
