package com.korov.alicloud.sentinel.controller;

import com.korov.alicloud.sentinel.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端无法实现自动注册，需要调用一次之后才会在控制端显示
 * 并且在启动的时候需要添加启动参数
 * -Dcsp.sentinel.dashboard.server=localhost:8854 -Dproject.name=Sentinel-Application
 */
@RestController
public class DemoController {
    @Autowired
    private DemoService service;

    @GetMapping("/sentinel/init")
    public void init() {
        service.init();
    }
}
