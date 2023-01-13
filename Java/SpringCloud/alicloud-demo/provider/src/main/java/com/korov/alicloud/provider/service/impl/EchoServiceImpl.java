package com.korov.alicloud.provider.service.impl;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.korov.alicloud.provider.service.EchoService;
import org.springframework.stereotype.Service;

@Service
public class EchoServiceImpl implements EchoService {
    private String nacosConfigName;

    @Override
    public String getNacosConfigName() {
        return nacosConfigName;
    }

    @NacosValue(value = "${config.new.name}", autoRefreshed = true)
    public void setNacosConfigName(String nacosConfigName) {
        this.nacosConfigName = nacosConfigName;
    }


}
