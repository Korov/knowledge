package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;


public abstract class Burger implements Item {

    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}

