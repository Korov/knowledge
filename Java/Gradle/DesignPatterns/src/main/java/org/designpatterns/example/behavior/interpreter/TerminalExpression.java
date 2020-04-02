package org.designpatterns.example.behavior.interpreter;

/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 * 终结符表达式
 */
public class TerminalExpression extends Expression {


    //通常终结符表达式只有一个，但是有多个对象
    @Override
    public Object interpreter(Context ctx) {
        return null;
    }

}
