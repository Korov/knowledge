package com.korov.springboot.dispatch;

public class Dispatch {
    static class QQ{}
    static class Baidu {}
    public static class Father{
        public void hardChoice(QQ arg){
            System.out.println("father choose QQ!");
        }
        public void hardChoice(Baidu arg){
            System.out.println("father choose Baidu!");
        }
    }
    public static class Son extends Father{
        public void hardChoice(QQ arg){
            System.out.println("son choose QQ!");
        }
        public void hardChoice(Baidu arg){
            System.out.println("son choose Baidu!");
        }
    }

    public static void main(String[] args) {
        Father father=new Father();
        Father son=new Son();
        father.hardChoice(new Baidu());
        son.hardChoice(new QQ());
    }
}
