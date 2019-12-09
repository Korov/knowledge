package com.korov.gradle.knowledge.controller;

import com.korov.gradle.knowledge.model.Managers;
import com.korov.gradle.knowledge.service.ManagersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class NerfortuneController {
    @Autowired
    private ManagersService service;

    @GetMapping(value = "/selectAll")
    public List<Managers> selectAll() {
        return service.selectAll();
    }

    @GetMapping(value = "/getRequest")
    public void getRequest(HttpServletRequest request){
        System.out.println(request.toString());
        System.out.println("debug");
    }
}
