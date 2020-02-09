package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.visitor;

public class ConcreteElement1 extends Element {
    @Override
    public void doSomething() {

    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
