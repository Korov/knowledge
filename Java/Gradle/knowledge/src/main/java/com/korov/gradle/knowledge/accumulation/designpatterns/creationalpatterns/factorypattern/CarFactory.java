package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.factorypattern;

import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.factorypattern.car.Benz;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.factorypattern.car.Car;

class CarFactory {
    static Car getCar(final String car) throws Exception {
        if (car.equalsIgnoreCase("Benz")) {
            return new Benz();
        } else if (car.equalsIgnoreCase("BMW")) {
            return new Benz();
        } else {
            throw new Exception();
        }
    }
}
