package com.korov.gradle.knowledge.accumulation.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 该组件实现了服务器对从客户端接收的数据的处理，即逻辑业务
 * <p>
 * ChannelInboundHandlerAdapter有一个直观的API，他的每个方法都可以被重写挂钩到事件声明周期的恰当点上
 * 因为需要处理所有接收到的数据，所以你重写了channelRead方法等
 *
 * @author korov
 * @date 2020/7/19
 */
@ChannelHandler.Sharable // 表示一个ChannelHandler可以被多个Channel安全的共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        // 将消息记录到控制台
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        // 将收到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 将未决消息冲刷到远程节点，并且关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 打印异常栈跟踪
        cause.printStackTrace();
        // 关闭Channel
        ctx.close();
    }
}
