package com.korov.dubbo.demo.service.impl;

import com.korov.dubbo.demo.service.ProviderService;

/**
 * xml方式服务提供者实现类
 */
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String sayHello(String word) {
        return "Hello " + word;
    }
}
