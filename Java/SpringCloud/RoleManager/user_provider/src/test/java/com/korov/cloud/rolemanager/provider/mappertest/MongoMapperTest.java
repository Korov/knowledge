package com.korov.cloud.rolemanager.provider.mappertest;

import com.korov.cloud.rolemanager.provider.MongoUserEntity;
import com.korov.cloud.rolemanager.provider.SpringbootApplicationTests;
import com.korov.cloud.rolemanager.provider.mongoservice.MongoUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoMapperTest extends SpringbootApplicationTests {
    @Autowired
    private MongoUserService mongoUserService;

    @Test
    public void test() {
        MongoUserEntity entity = new MongoUserEntity("1", "1", "个人简历.doc");
        mongoUserService.save(entity);
    }
}
