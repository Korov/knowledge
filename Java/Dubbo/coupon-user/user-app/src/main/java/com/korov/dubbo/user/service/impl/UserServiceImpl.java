package com.korov.dubbo.user.service.impl;

import com.korov.dubbo.user.domain.TUser;
import com.korov.dubbo.user.mapper.TUserMapper;
import com.korov.dubbo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TUserMapper userMapper;

    @Override
    public TUser queryUserById(Integer userId) {
        TUser user = userMapper.selectByPrimaryKey(userId);
        return user;
    }
}
