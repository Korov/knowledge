package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;

public class ConcreteBuilder implements Builder {
    @Override
    public void buildPartA() {
        System.out.println("Part A created");
    }

    @Override
    public void buildPartB() {
        System.out.println("Part B created");
    }

    @Override
    public void buildPartC() {
        System.out.println("Part C created");
    }

    @Override
    public Product getResult() {
        System.out.println("Product created");
        return null;
    }
}
