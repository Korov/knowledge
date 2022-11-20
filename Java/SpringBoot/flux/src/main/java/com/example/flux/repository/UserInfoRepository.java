package com.example.flux.repository;

import com.example.flux.entity.UserInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserInfoRepository extends ReactiveCrudRepository<UserInfo, Long> {
}
