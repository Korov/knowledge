package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.factorypattern;

import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.factorypattern.car.Car;

public class TestDemo {
    public static void main(final String[] args) {
        try {
            final Car car = CarFactory.getCar("benz");
            car.getCarName();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
