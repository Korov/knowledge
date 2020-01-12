package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;

/**
 * 食物条目
 */
public interface Item {
    public String name();

    public Packing packing();

    public float price();
}
