package com.korov.gradle.knowledge.accumulation.basic.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyAnnotationTest {

    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("com.korov.gradle.knowledge.accumulation.basic.annotation.MyAnnotationDemo");

        //获取类注解信息
        MyAnnotationDefinition classAnno = (MyAnnotationDefinition) clazz.getAnnotation(MyAnnotationDefinition.class);
        System.out.println(classAnno.name() + "---" + classAnno.value() + "---" + classAnno.path());

        //获取所以方法注解信息 ps:这里需要使用 isAnnotationPresent 判断方法上是否使用了注解
        Method[] allMethods = clazz.getDeclaredMethods();
        for (int i = 0; i < allMethods.length; i++) {
            if (allMethods[i].isAnnotationPresent(MyAnnotationDefinition.class)) {
                MyAnnotationDefinition methodAnno = allMethods[i].getAnnotation(MyAnnotationDefinition.class);
                System.out.println("遍历:当前方法名为：" + allMethods[i].getName() + " 的注解信息：---" + methodAnno.name() + "---" + methodAnno.value() + "---" + methodAnno.path());
            }
        }

        //获取指定方法注解信息
        Method methodTest = clazz.getDeclaredMethod("testAnno");
        MyAnnotationDefinition methodAnnotest = methodTest.getAnnotation(MyAnnotationDefinition.class);
        System.out.println(methodAnnotest.name() + "---" + methodAnnotest.value() + "---" + methodAnnotest.path());


        //获取属性注解信息
        Field nameField = clazz.getDeclaredField("name");
        MyAnnotationDefinition attrAnno = nameField.getAnnotation(MyAnnotationDefinition.class);
        System.out.println(attrAnno.name() + "---" + attrAnno.value() + "---" + attrAnno.path());
    }
}