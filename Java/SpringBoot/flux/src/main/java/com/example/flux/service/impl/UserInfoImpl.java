package com.example.flux.service.impl;

import com.example.flux.entity.UserInfo;
import com.example.flux.repository.UserInfoRepository;
import com.example.flux.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserInfoImpl implements UserInfoService {
    private UserInfoRepository userInfoRepository;

    @Autowired
    public void setUserInfoRepository(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public Mono<UserInfo> addUser(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public Mono<UserInfo> updateUser(UserInfo userInfo) {
        return userInfoRepository.findById(userInfo.getId())
                .map(info -> userInfo)
                .flatMap(userInfoRepository::save);
    }

    @Override
    public Mono<UserInfo> delete(Long id) {
        return userInfoRepository.findById(id)
                .flatMap(userInfo -> userInfoRepository.deleteById(userInfo.getId()).thenReturn(userInfo));
    }

    @Override
    public Flux<UserInfo> findAllUser() {
        return userInfoRepository.findAll();
    }
}
