package com.korov.cloud.rolemanager.provider.service;

import com.korov.cloud.rolemanager.provider.UserEntity;

public interface UserService {
    UserEntity selectByPrimaryKeyWithSleep(Long id);
}
