package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;


public class MealBuilder {

    public static Meal prepareVegMeal() {
        final Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    public static Meal prepareNonVegMeal() {
        final Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}

