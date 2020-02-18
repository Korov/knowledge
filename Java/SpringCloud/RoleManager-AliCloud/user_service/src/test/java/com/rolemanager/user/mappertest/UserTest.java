package com.rolemanager.user.mappertest;

import com.rolemanager.user.ApplicationTests;
import com.rolemanager.user.mapper.UserMapper;
import com.rolemanager.user.model.UserModel;
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
        UserModel user = userMapper.selectByPrimaryKey(1L);
        if (user == null) {
            user = new UserModel();
            user.setId(1L);
            user.setName("demo");
            long id = userMapper.insert(user);
            user = userMapper.selectByPrimaryKey(id);
        }
        log.info(user.toString());

        List<UserModel> userModels = userMapper.getUsers(0,30);
        System.out.println("debug");
    }
}
