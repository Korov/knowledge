package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.singletonpattern;

/**
 * 单例的双检锁/双重校验锁
 */
public class SingletonDCL {
    private volatile static SingletonDCL singleton;

    private SingletonDCL() {
    }

    public static SingletonDCL getSingleton() {
        if (singleton == null) {
            synchronized (SingletonDCL.class) {
                if (singleton == null) {
                    singleton = new SingletonDCL();
                }
            }
        }
        return singleton;
    }
}
