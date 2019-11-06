package com.korov.springboot.service.entityfullservice;

import com.korov.springboot.entity.entityfull.SysUser;

public interface IUserFullSevice {
    SysUser getUser(Integer userId);

    SysUser selectByUsername(String username);
}
