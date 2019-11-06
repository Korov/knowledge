package com.korov.springboot.aspect.aspectj;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration//作为配置文件之一
@ImportResource("classpath:aop/aop.xml")
public class AOPConfig {
}
