package com.korov.cloud.rolemanager.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigRefreshController {
    @Value("${info.app.name}")
    private String appName;

    @GetMapping("/profile")
    public String getProfile() {
        return this.appName;
    }
}
