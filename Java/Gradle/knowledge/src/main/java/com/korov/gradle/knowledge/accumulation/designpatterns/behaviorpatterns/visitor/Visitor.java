package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.visitor;

public interface Visitor {

    void visit(ConcreteElement1 element1);

    void visit(ConcreteElement2 element2);
}
