package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.command;

/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 * <p>
 * 通用命令模式
 * 默认对Receiver进行封装，每个命令完成单一的职责，而不是根据接收者的不同而完成不同的职责
 * 在高层模块的调用时就不用考虑接收者是谁的问题
 */
public class Client {

    public static void main(String[] args) {
        //首先声明出调用者Invoker
        Invoker invoker = new Invoker();

        //定义一个发送给接收者的命令
        Command command = new ConcreteCommand1();

        //把命令交给调用者去执行
        invoker.setCommand(command);
        invoker.action();

    }

}
