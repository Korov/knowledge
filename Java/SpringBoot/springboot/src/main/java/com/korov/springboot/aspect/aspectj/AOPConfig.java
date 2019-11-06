package com.korov.springboot.aspect.aspectj;

import org.springframework.context.annotation.Configuration;

/**
 * 1.安装按aspectj，确保IDE支持aspectj
 * 2.添加aspectj的jar包：aspectjweaver，aspectjrt
 * 3.在idea中需要设置Java Compiler为Ajc，并设置aspectj的jar包位置
 */
@Configuration//作为配置文件之一
//@ImportResource("classpath:aop/aop.xml")
public class AOPConfig {
}
