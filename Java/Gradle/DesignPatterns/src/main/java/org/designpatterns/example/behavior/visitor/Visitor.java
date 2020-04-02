package org.designpatterns.example.behavior.visitor;

public interface Visitor {

    void visit(ConcreteElement1 element1);

    void visit(ConcreteElement2 element2);
}
