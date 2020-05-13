package com.korov.dubbo.provider.demo.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.korov.dubbo.provider.demo.service.ProviderService;

/**
 * xml方式服务提供者实现类
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String sayHello(String word) {
        return "Hello with annotation: " + word;
    }
}
