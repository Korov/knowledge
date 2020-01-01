package com.korov.springboot.ThreadTest.Thread;

/**
 * 增加了懒汉特性，能够实现单实例，懒加载以及高性能的特点
 */
public class SingletonEnumImprove {
    private byte[] data = new byte[1024];

    private SingletonEnumImprove() {

    }

    public static SingletonEnumImprove getInstance() {
        return EnumHolder.INSTANCE.getSingleton();
    }

    private enum EnumHolder {
        INSTANCE;
        private SingletonEnumImprove instance;

        EnumHolder() {
            this.instance = new SingletonEnumImprove();
        }

        private SingletonEnumImprove getSingleton() {
            return instance;
        }
    }
}
