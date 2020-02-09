package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.visitor;

public abstract class Element {
    // 定义业务逻辑
    public abstract void doSomething();

    // 允许谁来访问
    public abstract void accept(Visitor visitor);
}
