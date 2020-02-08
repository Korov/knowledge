package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.proxypatterns;

/**
 * 代理类，代理模式的核心
 * <p>
 * 一个代理类可以代理多个被委托者或被代理者， 因此一个代理类具体代理哪个真实主题
 * 角色， 是由场景类决定的。 当然， 最简单的情况就是一个主题类和一个代理类， 这是最简洁
 * 的代理模式。 在通常情况下， 一个接口只需要一个代理类就可以了， 具体代理哪个实现类由
 * 高层模块来决定， 也就是在代理类的构造函数中传递被代理者
 */
public class Proxy implements Subject {
    //要代理哪个实现类
    private Subject subject = null;

    // 默认被代理者
    public Proxy() {
        subject = new Proxy();
    }

    // 通过构造函数传递代理者
    public Proxy(Object... objects) {

    }

    @Override
    public void request() {
        before();
        subject.request();
        after();
    }

    // 预处理
    private void before() {
        System.out.println("代理类的预处理");
    }

    // 善后处理
    private void after() {
        System.out.println("代理类的善后处理");
    }
}
