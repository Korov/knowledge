package org.designpatterns.example.behavior.template;

public class MyTemplate2 extends TemplateClass {
    @Override
    protected void doSomething() {
        System.out.println("模板方法修改2");
    }

    @Override
    protected void doAnything() {
        System.out.println("模板方法实现2");
    }
}
