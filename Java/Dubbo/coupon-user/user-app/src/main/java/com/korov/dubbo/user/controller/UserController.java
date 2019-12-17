package com.korov.dubbo.user.controller;

import com.alibaba.fastjson.JSON;
import com.korov.dubbo.user.constant.Constant;
import com.korov.dubbo.user.domain.TUser;
import com.korov.dubbo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public String queryUserById(Integer userId) {
        if (userId == null) {
            return Constant.SPACE;
        }
        TUser user = userService.queryUserById(userId);
        return JSON.toJSONString(user);
    }
}
