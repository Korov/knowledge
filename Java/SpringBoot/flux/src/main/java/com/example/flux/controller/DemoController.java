package com.example.flux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public Mono<String> echo(@PathVariable String string) {
        log.info("provider get str:{}", string);
        return Mono.just(String.format("get user %s", string));
    }
}
