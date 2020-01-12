package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.builderpattern;


public class TestDemo {
    public static void main(final String[] args) {
        final MealBuilder mealBuilder = new MealBuilder();

        final Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("Total Cost: " + vegMeal.getCost());

        final Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.println("\n\nNon-Veg Meal");
        nonVegMeal.showItems();
        System.out.println("Total Cost: " + nonVegMeal.getCost());
    }
}

