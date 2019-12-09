package com.korov.gradle.knowledge.accumulation.Thread.Singleton;


/**
 * Holder 方式的单例设计师最好的设计之一，也是目前使用比较广泛的设计之一
 */
public class SingletonHolder {
    private final byte[] data = new byte[1024];

    private SingletonHolder() {
    }

    public static SingletonHolder getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final SingletonHolder instance = new SingletonHolder();
    }
}
