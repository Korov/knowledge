package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.singletonpattern;

public class TestDemo {
    public static void main(final String[] args) {
        //不合法的构造函数
        //编译时错误：构造函数 SingleObject() 是不可见的
        //SingleObject object = new SingleObject();

        //获取唯一可用的对象
        final SingleObject object = SingleObject.getInstance();

        final SingleObject object1 = SingleObject.getInstance();

        System.out.println(object == object1);
    }
}
