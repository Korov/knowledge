package com.korov.cloud.rolemanager.consumer.controller;

import com.korov.cloud.rolemanager.provider.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class AngularController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String KOROV_PROVIDER_URL = "http://rolemanager-provider";

    @GetMapping("/consumer/users")
    public List<UserEntity> getUsers() {
        return restTemplate.getForObject(KOROV_PROVIDER_URL + "/users", List.class);
    }


    @RequestMapping(value = "/consumer/users/getid/{id}", method = RequestMethod.GET)
    public UserEntity getUserById(@PathVariable("id") long userId) {
        return restTemplate.getForObject(KOROV_PROVIDER_URL + "/users/getid/" + userId, UserEntity.class);
    }


    @RequestMapping(value = "/consumer/users/getname", method = RequestMethod.GET)
    public List<UserEntity> getUserByName(@RequestParam("name") String userName) {
        return restTemplate.getForObject(KOROV_PROVIDER_URL + "/users", List.class);
    }

    @RequestMapping(value = "/consumer/users/adduser", method = RequestMethod.POST)
    public void addUser(@RequestBody String user) {
        restTemplate.postForEntity(KOROV_PROVIDER_URL + "/users/adduser", user, String.class);
    }

    @RequestMapping(value = "/consumer/users/deleteuser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") long userId) {
        restTemplate.delete(KOROV_PROVIDER_URL + "/users/deleteuser/" + userId);
    }

    @RequestMapping(value = "/consumer/users/updateuser", method = RequestMethod.PUT)
    public void updateUser(@RequestBody String user) {
        restTemplate.put(KOROV_PROVIDER_URL + "/users/updateuser", user, String.class);
    }
}
