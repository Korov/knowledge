package org.designpatterns.example.structural.bridge;

/**
 * 为什么要增加一个构造函数？ 答案是为了提醒子类， 你必须做这项工
 * 作， 指定实现者， 特别是已经明确了实现者， 则尽量清晰明确地定义出来。
 */
public abstract class Abstraction {
    //定义对实现化角色的引用
    private Implementor implementor;

    //约束子类必须实现该构造函数
    public Abstraction(Implementor implementor) {
        implementor = implementor;
    }

    //自身的行为和属性
    public void request() {
        implementor.doSomething();
    }

    //获得实现化角色
    public Implementor getImp() {
        return implementor;
    }
}
