package com.korov.dubbo.provider.demo;

import com.korov.dubbo.provider.demo.config.ProviderConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * xml方式启动
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        System.in.read(); // 按任意键退出
    }
}
