package org.designpatterns.example.behavior.chainofresponsibility;


/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 * <p>
 * ①定义一个请求处理方法handleMessage
 * ②定义一个链的编排方法,setNext();
 * ③定义自己能处理的级别和具体处理业务的方法
 */
public abstract class Handler {
    private Handler nextHandler;

    //每个处理者都必须对请求做出处理
    public final Response handlerMessage(Request request) {
        Response response = null;

        //判断是否是自己的处理级别
        if (getHandlerLevel().equals(request.getRequestLevel())) {
            response = echo(request);
        } else {  //不属于自己的处理级别
            //判断是否有下一个处理者
            if (nextHandler != null) {
                response = nextHandler.handlerMessage(request);
            } else {
                //没有适当的处理者，业务自行处理
            }
        }
        return response;
    }


    //设置下一个处理者是谁
    public void setNext(Handler _handler) {
		nextHandler = _handler;
    }

    //每个处理者都有一个处理级别
    protected abstract Level getHandlerLevel();

    //每个处理者都必须实现处理任务
    protected abstract Response echo(Request request);

}
