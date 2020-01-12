package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.factory;

import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.color.Blue;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.color.Color;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.color.Green;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.color.Red;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.shape.Shape;

public class ColorFactory extends AbstractFactory {
    @Override
    public Shape getShape(final String shapeType) {
        return null;
    }

    @Override
    public Color getColor(final String color) {
        if (color == null) {
            return null;
        }
        if (color.equalsIgnoreCase("RED")) {
            return new Red();
        } else if (color.equalsIgnoreCase("GREEN")) {
            return new Green();
        } else if (color.equalsIgnoreCase("BLUE")) {
            return new Blue();
        }
        return null;
    }
}
