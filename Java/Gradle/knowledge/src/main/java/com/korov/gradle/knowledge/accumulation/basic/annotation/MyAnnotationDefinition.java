package com.korov.gradle.knowledge.accumulation.basic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author korov
 * @date
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyAnnotationDefinition {
    /*定义注解里面的参数信息*/
    String name();

    String value();

    String path();
}
