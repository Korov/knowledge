package org.designpatterns.example.creational.abstractfactory.factory;


import org.designpatterns.example.creational.abstractfactory.color.Blue;
import org.designpatterns.example.creational.abstractfactory.color.Color;
import org.designpatterns.example.creational.abstractfactory.color.Green;
import org.designpatterns.example.creational.abstractfactory.color.Red;
import org.designpatterns.example.creational.abstractfactory.shape.Shape;

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
