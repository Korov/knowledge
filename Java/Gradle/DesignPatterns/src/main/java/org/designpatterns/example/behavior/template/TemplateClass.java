package org.designpatterns.example.behavior.template;

/**
 * 抽象模板
 */
public abstract class TemplateClass {
    // 基本方法
    protected abstract void doSomething();

    // 基本方法
    protected abstract void doAnything();

    // 模板方法
    public void templateMethod() {
        doSomething();
        doAnything();
    }
}
