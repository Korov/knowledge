package org.korov.spring3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @GetMapping(path = "/hello")
    public String hello(@RequestParam("name") String name) {
        log.info("hello {}", name);
        return String.format("hello %s", name);
    }
}
