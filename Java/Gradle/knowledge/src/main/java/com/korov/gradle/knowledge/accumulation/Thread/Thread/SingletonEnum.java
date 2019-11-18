package com.korov.gradle.knowledge.accumulation.Thread.Thread;

/**
 * 原始方法
 */
public enum SingletonEnum {
    INSTANCE;

    private final byte[] data = new byte[1024];

    SingletonEnum() {
        System.out.println("INSTANCE will be initialized");
    }

    public static void method() {
        //调用该方法则会主动使用Singleton，INSTACNE将会被实例化
    }

    public static SingletonEnum getInstance() {
        return INSTANCE;
    }
}
