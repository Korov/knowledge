package org.designpatterns.example.behavior.state;

/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 * <p>
 * 通用状态模式
 * <p>
 * 状态模式适用于当某个对象在它的状态发生改变时，它的行为也随着发生比较大的变化，
 * 也就是说在行为受状态约束的情况下可以使用状态模式，而且使用时对象的状态最好不要超过5个。
 */
public class Client {

    public static void main(String[] args) {
        //定义环境角色
        Context context = new Context();
        //初始化状态
        context.setCurrentState(new ConcreteState1());
        //行为执行
        context.handle1();
        context.handle2();
    }
}
