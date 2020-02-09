package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.visitor;

public class VisitorImpl implements Visitor {
    @Override
    public void visit(ConcreteElement1 element1) {
        element1.doSomething();
    }

    @Override
    public void visit(ConcreteElement2 element2) {
        element2.doSomething();
    }
}
