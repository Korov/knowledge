package com.korov.springboot.ThreadTest.FutureTask;

public class ProductionInfo implements ProductInfo {
    private String name;

    public ProductionInfo(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
