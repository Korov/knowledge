package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.shape;

public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
