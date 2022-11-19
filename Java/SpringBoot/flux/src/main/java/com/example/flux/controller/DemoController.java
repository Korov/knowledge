package com.example.flux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RestController
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public Mono<String> echo(@PathVariable String string) {
        log.info("provider get str:{}", string);
        // Mono<String> anotherResult = Mono.just(String.format("get user %s", string));
        Mono<String> result = Mono.fromSupplier(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return String.format("get user %s", string);
        });
        // 这个Mono是立即返回，没有等待5秒钟，这时这个线程可以去做其他的事情
        log.info("provider return mono");
        return result;
    }
}
