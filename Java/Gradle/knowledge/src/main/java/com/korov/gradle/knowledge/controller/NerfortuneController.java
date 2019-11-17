package com.korov.gradle.knowledge.controller;

import com.korov.gradle.knowledge.model.Newfortune;
import com.korov.gradle.knowledge.service.NewfortuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NerfortuneController {
    @Autowired
    private NewfortuneService service;

    @GetMapping(value = "/selectAll")
    public List<Newfortune> selectAll() {
        return service.selectAll();
    }
}
