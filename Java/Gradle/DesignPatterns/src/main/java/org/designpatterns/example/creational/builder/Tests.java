package org.designpatterns.example.creational.builder;

import org.designpatterns.example.creational.builder.builder.Builder;
import org.designpatterns.example.creational.builder.builder.impl.BuilderImpl;
import org.designpatterns.example.creational.builder.production.Product;

public class Tests {
    public static void main(String[] args) {
        Builder builder = new BuilderImpl();
        Director director = new Director(builder);

        director.construct();
        Product product = builder.getResult();
    }
}
