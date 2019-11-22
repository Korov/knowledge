package com.korov.cloud.rolemanager.provider.mongoservice.impl;

import com.korov.cloud.rolemanager.provider.MongoUserEntity;
import com.korov.cloud.rolemanager.provider.mongomapper.MongoUserMapper;
import com.korov.cloud.rolemanager.provider.mongoservice.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoUserServiceImpl implements MongoUserService {

    @Autowired
    private MongoUserMapper mongoUserMapper;

    @Override
    public MongoUserEntity save(MongoUserEntity entity) {
        return mongoUserMapper.save(entity);
    }
}
