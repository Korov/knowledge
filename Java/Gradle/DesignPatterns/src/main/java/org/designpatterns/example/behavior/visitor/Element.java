package org.designpatterns.example.behavior.visitor;

public abstract class Element {
    // 定义业务逻辑
    public abstract void doSomething();

    // 允许谁来访问
    public abstract void accept(Visitor visitor);
}
