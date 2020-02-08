package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;

public class DemoTest {
    public static void main(String[] args) {
        ConcreteBuilder builder = new ConcreteBuilder();
        Director director = new Director(builder);

        director.construct();
        Product product = builder.getResult();
    }
}
