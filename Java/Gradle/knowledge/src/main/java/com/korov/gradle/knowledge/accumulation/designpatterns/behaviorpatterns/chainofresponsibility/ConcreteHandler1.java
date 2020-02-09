package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.chainofresponsibility;

/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 */
public class ConcreteHandler1 extends Handler {
    //定义自己的处理逻辑
    @Override
	protected Response echo(Request request) {
        //完成处理逻辑
        return null;
    }

    //设置自己的处理级别
    @Override
	protected Level getHandlerLevel() {
        //设置自己的处理级别
        return null;
    }

}
