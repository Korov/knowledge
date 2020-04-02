package org.designpatterns.example.behavior.visitor;

public class ConcreteElement1 extends Element {
    @Override
    public void doSomething() {

    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
