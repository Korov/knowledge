package com.korov.cloud.rolemanager.provider.mongoservice;

import com.korov.cloud.rolemanager.provider.MongoUserEntity;

public interface MongoUserService {
    MongoUserEntity save(MongoUserEntity entity);
}
