package org.designpatterns.example.creational.abstractfactory.color;

public class Green implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Green::fill() method.");
    }
}
