package org.designpatterns.example.behavior.visitor;

public class ConcreteElement2 extends Element {
    @Override
    public void doSomething() {

    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
