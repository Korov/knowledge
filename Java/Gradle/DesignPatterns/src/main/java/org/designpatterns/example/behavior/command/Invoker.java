package org.designpatterns.example.behavior.command;

/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 */
public class Invoker {
    private Command command;

    //受气包，接受命令
    public void setCommand(Command _command) {
        command = _command;
    }

    //执行命令
    public void action() {
        command.execute();
    }

}
