package org.designpatterns.example.demo.dispatch;

public class Test {
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
        Man man1 = new Man();
        Woman woman1 = new Woman();
        man1.sayHello();
        woman1.sayHello();
    }
}
