package com.example.flux.controller;

import com.example.flux.entity.UserInfo;
import com.example.flux.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserInfo> addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @GetMapping("/user")
    public Flux<UserInfo> getAllUser() {
        return userInfoService.findAllUser();
    }

    @PutMapping("/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<UserInfo> updateUser(@RequestBody UserInfo userInfo) {
        return userInfoService.updateUser(userInfo);
    }

    @DeleteMapping("/user")
    public Mono<UserInfo> delete(Long id) {
        return userInfoService.delete(id);
    }

    @GetMapping("/user")
    public Flux<UserInfo> getAllUser(@RequestParam("offset") long offset, @RequestParam("size") long size) {
        return userInfoService.findAllUser(offset, size);
    }
}
