package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.bridgepatterns;

public class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    // 修正父类的行为
    @Override
    public void request() {
        super.request();
        super.getImp().doAnything();
    }
}
