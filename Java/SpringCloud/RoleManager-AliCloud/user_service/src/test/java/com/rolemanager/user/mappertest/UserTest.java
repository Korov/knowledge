package com.rolemanager.user.mappertest;

import com.rolemanager.user.ApplicationTests;
import com.rolemanager.user.mapper.UserMapper;
import com.rolemanager.user.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        UserModel userModel = userMapper.selectByPrimaryKey(1L);
        if (userModel == null) {
            userModel = new UserModel();
            userModel.setId(1L);
            userModel.setName("demo");
            userMapper.insert(userModel);
        }
        userModel = userMapper.selectByPrimaryKey(1L);
        log.info(userModel.toString());
    }
}
