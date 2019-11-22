package com.korov.cloud.rolemanager.provider.service.impl;

import com.korov.cloud.rolemanager.provider.UserEntity;
import com.korov.cloud.rolemanager.provider.mapper.IUserMapper;
import com.korov.cloud.rolemanager.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IUserMapper userMapper;

    @Override
    public UserEntity selectByPrimaryKeyWithSleep(Long userId) {
        redisTemplate.opsForValue().set("userId:" + userId, userId);

        redisTemplate.opsForValue().set("userId:" + userId, "update Test");

        boolean isHaveKey = redisTemplate.hasKey("userId:" + userId);
        boolean isHaveKeyTmp = redisTemplate.hasKey("NullKey");


        String value = (String) redisTemplate.opsForValue().get("userId:" + userId);

        stringRedisTemplate.opsForValue().set("StringUserId", "String Update Test");
        String stringValue = stringRedisTemplate.opsForValue().get("StringUserId");
        UserEntity user = userMapper.selectByPrimaryKey(userId);
        return user;
    }
}
