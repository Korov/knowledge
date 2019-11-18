package com.korov.gradle.knowledge.accumulation.Thread.Thread;

/**
 * 增加了懒汉特性，能够实现单实例，懒加载以及高性能的特点
 */
public class SingletonEnumImprove {
    private final byte[] data = new byte[1024];

    private SingletonEnumImprove() {

    }

    public static SingletonEnumImprove getInstance() {
        return EnumHolder.INSTANCE.getSingleton();
    }

    private enum EnumHolder {
        INSTANCE;
        private final SingletonEnumImprove instance;

        EnumHolder() {
            instance = new SingletonEnumImprove();
        }

        private SingletonEnumImprove getSingleton() {
            return instance;
        }
    }
}
