package com.korov.cloud.rolemanager.provider.mongomapper;

import com.korov.cloud.rolemanager.provider.MongoUserEntity;
import com.korov.cloud.rolemanager.provider.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserMapper extends MongoRepository<MongoUserEntity, String> {
}
