package com.rolemanager.oauth.service;

import com.rolemanager.commons.model.users.UserModel;

import java.util.List;

public interface OAuthService {
    UserModel getUserByUsername(String username);

    List<String> findPermissionsByUserId(long userId);
}
