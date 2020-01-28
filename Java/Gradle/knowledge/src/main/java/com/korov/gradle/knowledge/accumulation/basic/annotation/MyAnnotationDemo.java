package com.korov.gradle.knowledge.accumulation.basic.annotation;

@MyAnnotationDefinition(name = "类名称", value = "类值", path = "类路径")
public class MyAnnotationDemo {

    @MyAnnotationDefinition(name = "属性名", value = "属性值", path = "属性路径")
    private String name;

    @MyAnnotationDefinition(name = "年龄", value = "18", path = "/user2")
    private String age;

    @MyAnnotationDefinition(name = "方法名", value = "方法值", path = "方法访问路径")
    public String testAnno() {
        return "successs!!!";
    }

    @MyAnnotationDefinition(name = "方法名1", value = "方法值1", path = "方法访问路径1")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
