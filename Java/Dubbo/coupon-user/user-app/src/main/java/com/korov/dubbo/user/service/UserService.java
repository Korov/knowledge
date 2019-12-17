package com.korov.dubbo.user.service;

import com.korov.dubbo.user.domain.TUser;

public interface UserService {
    TUser queryUserById(Integer userId);
}
