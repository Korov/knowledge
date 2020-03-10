package com.rolemanager.oauth.service.impl;

import com.rolemanager.commons.model.users.PermissionModel;
import com.rolemanager.commons.model.users.UserModel;
import com.rolemanager.oauth.mapper.OAuthMapper;
import com.rolemanager.oauth.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private OAuthMapper oAuthMapper;

    @Override
    public UserModel getUserByUsername(String nickname) {
        return oAuthMapper.getUserByName(nickname);
    }

    @Override
    public List<String> findPermissionsByUserId(long userId) {
        List<PermissionModel> permissions = oAuthMapper.getPermissionsByUserId(userId);
        List<String> permissionCodes = new ArrayList<>();
        permissions.stream().forEach(permissionModel -> {
            permissionCodes.add(permissionModel.getPermissionCode());
        });
        return permissionCodes;
    }
}
