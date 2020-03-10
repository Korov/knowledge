package com.rolemanager.user.mappertest;

import com.rolemanager.commons.model.UserModel;
import com.rolemanager.user.ApplicationTests;
import com.rolemanager.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        UserModel user = userMapper.getUserByName("demo");
        if (user == null) {
            user = new UserModel();
            user.setNickname("demo");
            userMapper.insert(user);
            user = userMapper.getUserByName("demo");
        }
        log.info(user.toString());

        List<UserModel> userModels = userMapper.getUsers(0, 30);
        System.out.println("debug");
    }
}
