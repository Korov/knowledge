package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.visitor;

public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            // 获得元素对象
            Element element = ObjectStruture.getElement();
            element.accept(new VisitorImpl());
        }
    }
}
