package com.korov.dubbo.user.mappertest;

import com.korov.dubbo.user.SpringbootApplicationTests;
import com.korov.dubbo.user.domain.TUser;
import com.korov.dubbo.user.mapper.TUserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest extends SpringbootApplicationTests {
    @Autowired
    private TUserMapper userMapper;

    @Test
    public void test() {
        TUser user = userMapper.selectByPrimaryKey(1);
        System.out.println("debug");
    }
}
