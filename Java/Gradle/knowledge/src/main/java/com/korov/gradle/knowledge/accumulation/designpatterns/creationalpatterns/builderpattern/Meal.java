package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private final List<Item> items = new ArrayList<Item>();

    public void addItem(final Item item) {
        this.items.add(item);
    }

    public float getCost() {
        float cost = 0.0f;
        for (final Item item : this.items) {
            cost += item.price();
        }
        return cost;
    }

    public void showItems() {
        for (final Item item : this.items) {
            System.out.print("Item : " + item.name());
            System.out.print(", Packing : " + item.packing().pack());
            System.out.println(", Price : " + item.price());
        }
    }
}
