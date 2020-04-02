package org.designpatterns.example.behavior.command;

/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 */
public class ConcreteCommand1 extends Command {
    //声明自己的默认接收者
    public ConcreteCommand1() {
        super(new ConcreteReciver1());
    }

    //设置新的接收者
    public ConcreteCommand1(Receiver _receiver) {
        super(_receiver);
    }

    //每个具体的命令都必须实现一个命令
    @Override
    public void execute() {
        //业务处理
        super.receiver.doSomething();
    }
}
