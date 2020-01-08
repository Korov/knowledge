package com.korov.cloud.rolemanager.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.korov.cloud.rolemanager.authenservice.intercepter.UserContextHolder;
import com.korov.cloud.rolemanager.provider.UserEntity;
import com.korov.cloud.rolemanager.provider.mapper.IUserMapper;
import com.korov.cloud.rolemanager.provider.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private IUserMapper userMapper;

    @GetMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "get_fallbackMethodById")
    public UserEntity findById(@PathVariable long userId) {
        UserEntity user = userMapper.selectByPrimaryKey(userId);
        log.info(user.toString());
        return user;
    }

    @GetMapping("/sleep/{userId}")
    public UserEntity selectByPrimaryKeyWithSleep(@PathVariable long userId) {
        UserEntity user = userService.selectByPrimaryKeyWithSleep(userId);
        log.info(user.toString());
        return user;
    }

    public UserEntity get_fallbackMethodById(@PathVariable long userId) {
        UserEntity entity = new UserEntity();
        entity.setId(0L).setName("not find");
        return entity;
    }

//    @RabbitListener(queues = "user")
//    @RabbitHandler
//    public void addUser(String user) {
//        UserEntity entity = JSONObject.parseObject(user, UserEntity.class);
//        userMapper.insert(entity);
//    }

//    @GetMapping("/{userName}")
//    @HystrixCommand(fallbackMethod = "get_fallbackMethodByName")
//    public UserEntity findByName(@PathVariable String userName) {
//        UserEntity user = userMapper.selectByUserName(userName);
//        log.info(user.toString());
//        return user;
//    }
//
//    public UserEntity get_fallbackMethodByName(@PathVariable String userName) {
//        UserEntity entity = new UserEntity();
//        entity.setId(0L).setName(userName + "not find");
//        return entity;
//    }


    @GetMapping("/provider/test")
    public String test(HttpServletRequest request) {
        System.out.println("auth success, the user is : " + UserContextHolder.currentUser().getUserName());
        System.out.println("----------------success access provider service----------------");
        return "success access provider service!";
    }
}
