package com.korov.dubbo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * xml方式启动
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo/config/provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
