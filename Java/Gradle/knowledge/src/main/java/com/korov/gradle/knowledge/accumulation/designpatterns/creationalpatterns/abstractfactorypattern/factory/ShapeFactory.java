package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.factory;

import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.color.Color;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.shape.Circle;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.shape.Rectangle;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.shape.Shape;
import com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.abstractfactorypattern.shape.Square;

public class ShapeFactory extends AbstractFactory {
    @Override
    public Shape getShape(final String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }

    @Override
    public Color getColor(final String color) {
        return null;
    }
}
