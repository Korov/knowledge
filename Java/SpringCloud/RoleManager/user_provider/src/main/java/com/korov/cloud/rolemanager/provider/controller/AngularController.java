package com.korov.cloud.rolemanager.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.korov.cloud.rolemanager.provider.UserEntity;
import com.korov.cloud.rolemanager.provider.mapper.IUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class AngularController {
    @Autowired
    private IUserMapper userMapper;

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        List<UserEntity> users = userMapper.selectAll();
        log.info(users.toString());
        //return JSONObject.toJSONString(users);
        return users;
    }

//    @GetMapping("/users/{userId}")
//    public UserEntity findById(@PathVariable long userId) {
//        UserEntity user = userMapper.selectByPrimaryKey(userId);
//        log.info(user.toString());
//        return user;
//    }

    @RequestMapping(value = "/users/getid/{id}", method = RequestMethod.GET)
    public UserEntity getUserById(@PathVariable("id") long userId) {
        UserEntity user = userMapper.selectByPrimaryKey(userId);
        log.info(user.toString());
        return user;
    }


    @RequestMapping(value = "/users/getname", method = RequestMethod.GET)
    public List<UserEntity> getUserByName(@RequestParam("name") String userName) {
        UserEntity user = userMapper.selectByUserName(userName);
        List<UserEntity> userEntities = new ArrayList<>();
        log.info(user.toString());
        userEntities.add(user);
        return userEntities;
    }

    @RequestMapping(value = "/users/adduser", method = RequestMethod.POST)
    public void addUser(@RequestBody String user) {
        UserEntity entity = JSONObject.parseObject(user, UserEntity.class);
        userMapper.insert(entity);
    }

    @RequestMapping(value = "/users/deleteuser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") long userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    @RequestMapping(value = "/users/updateuser", method = RequestMethod.PUT)
    public void updateUser(@RequestBody String user) {
        UserEntity entity = JSONObject.parseObject(user, UserEntity.class);
        userMapper.updateByPrimaryKey(entity);
    }
}
