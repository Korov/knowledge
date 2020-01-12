package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;


public class ChickenBurger extends Burger {

    @Override
    public float price() {
        return 50.5f;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }
}

