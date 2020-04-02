package org.designpatterns.example.creational.builder;

public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    /**
     * 将部件partA partB partC最后组成复杂对象
     * 这里是将车轮，方向盘和发动机组成成汽车的过程
     */
    public void construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
    }
}
