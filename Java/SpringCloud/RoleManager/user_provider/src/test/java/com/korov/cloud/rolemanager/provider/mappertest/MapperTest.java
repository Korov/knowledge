package com.korov.cloud.rolemanager.provider.mappertest;

import com.korov.cloud.rolemanager.provider.SpringbootApplicationTests;
import com.korov.cloud.rolemanager.provider.UserEntity;
import com.korov.cloud.rolemanager.provider.mapper.IUserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MapperTest extends SpringbootApplicationTests {
    @Autowired
    private IUserMapper mapper;

    @Test
    public void test() {
        List<UserEntity> entities = mapper.selectAll();
        UserEntity entity = mapper.selectByUserName("葱油饼");
        System.out.println("com/korov/cloud/rolemanager/provider");
    }
}
