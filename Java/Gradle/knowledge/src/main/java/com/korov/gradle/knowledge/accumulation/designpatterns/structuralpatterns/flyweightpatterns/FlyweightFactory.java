package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.flyweightpatterns;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
    // 定义一个池容器
    private static Map<String, Flyweight> pool = new HashMap<>();

    // 享元工厂
    public static Flyweight getFlyweight(String intrinsic) {
        // 需要返回的对象
        Flyweight flyweight = null;
        // 在池中没有该对象
        if (pool.containsKey(intrinsic)) {
            flyweight = pool.get(intrinsic);
        } else {
            // 根据外部状态创建享元对象
            flyweight = new ConcreteFlyweight1(intrinsic);
            // 将享元对象存放到池中
            pool.put(intrinsic, flyweight);
        }
        return flyweight;
    }
}
