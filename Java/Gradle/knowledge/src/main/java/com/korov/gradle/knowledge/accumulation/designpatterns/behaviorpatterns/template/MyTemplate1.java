package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.template;

public class MyTemplate1 extends TemplateClass {
    @Override
    protected void doSomething() {
        System.out.println("模板方法修改1");
    }

    @Override
    protected void doAnything() {
        System.out.println("模板方法实现1");
    }
}
