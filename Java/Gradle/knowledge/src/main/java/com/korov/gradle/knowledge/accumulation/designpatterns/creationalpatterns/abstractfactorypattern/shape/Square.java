package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.shape;

public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
