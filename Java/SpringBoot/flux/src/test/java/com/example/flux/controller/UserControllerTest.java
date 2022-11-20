package com.example.flux.controller;

import com.example.flux.FluxApplicationTests;
import com.example.flux.entity.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

class UserControllerTest extends FluxApplicationTests {
    private WebTestClient webTestClient;

    @Autowired
    public void setWebTestClient(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void addUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("wangmazi");
        webTestClient
                .post()
                .uri("/user")
                .body(BodyInserters.fromValue(userInfo))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserInfo.class)
                .consumeWith(System.out::println)
                .value(result -> Assertions.assertNotNull(result.getId()));

    }

    @Test
    void getAllUser() {
        webTestClient
                .get()
                .uri("/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class)
                .consumeWith(System.out::println);
    }

    @Test
    void updateUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUserName("zhangsan_update");
        webTestClient
                .put()
                .uri("/user")
                .body(BodyInserters.fromValue(userInfo))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserInfo.class)
                .consumeWith(System.out::println)
                .value(result -> Assertions.assertEquals(userInfo.getUserName(), result.getUserName()));
    }

    @Test
    void delete() {
        webTestClient
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/user").queryParam("id", 2).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserInfo.class)
                .consumeWith(System.out::println);
    }
}