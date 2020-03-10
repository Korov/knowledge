package com.rolemanager.oauth.mapper;

import com.rolemanager.commons.model.users.PermissionModel;
import com.rolemanager.commons.model.users.UserModel;

import java.util.List;

public interface OAuthMapper {


    UserModel selectByPrimaryKey(long id);

    UserModel getUserByName(String nickname);

    List<PermissionModel> getPermissionsByUserId(long userId);
}
