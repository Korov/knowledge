package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.singletonpattern;


class SingleObject {

    //创建 SingleObject 的一个对象
    private static SingleObject instance = new SingleObject();


    //让构造函数为 private，这样该类就不会被实例化
    private SingleObject() {
    }

    //获取唯一可用的对象
    static SingleObject getInstance() {
        if (instance == null) {
            instance = new SingleObject();
        }
        return instance;
    }

    static void showMessage() {
        System.out.println("Hello World!");
    }
}

