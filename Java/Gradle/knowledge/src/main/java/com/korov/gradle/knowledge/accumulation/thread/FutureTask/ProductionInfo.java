package com.korov.gradle.knowledge.accumulation.thread.FutureTask;

public class ProductionInfo implements ProductInfo {
    private String name;

    ProductionInfo(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
