package com.example.flux.service;

import com.example.flux.entity.UserInfo;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserInfoService {

    Mono<UserInfo> addUser(UserInfo userInfo);

    Mono<UserInfo> updateUser(UserInfo userInfo);

    Mono<UserInfo> delete(Long id);

    Flux<UserInfo> findAllUser();

    Flux<UserInfo> findAllUser(long offset, long size);
}
