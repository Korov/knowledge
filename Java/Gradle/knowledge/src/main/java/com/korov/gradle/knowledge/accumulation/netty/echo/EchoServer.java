package com.korov.gradle.knowledge.accumulation.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 创建了一个ServerBootstrap，因为你正在使用NIO传世，所以你指定了NioEventLoopGroup来接受和处理新的连接
 * 并且将Channel的类型指定为NioServerSocketChannel，你将本地地址设置为一个具有选定端口的InetSocketAddress
 * 服务器将绑定到这个地址以监听新的连接请求
 * <p>
 * ChannelInitializer是非常关键的，当一个新的连接被接受时，一个新的子Channel将会被创建，而ChannelInitializer
 * 将会把一个你的EchoServerHandler的实例添加到该Channel的ChannelPipline中，这个ChannelHandler将会收到有关入站消息的通知
 *
 * @author korov
 * @date 2020/7/19
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 9999;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    // 指定使用NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个EchoServerHandler到子Channel的ChannelPipline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            // EchoServerHandler被标注为@Shareable，所以我们可以总是使用同样的实例
                            channel.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步的绑定服务器，调用sync方法阻塞等待直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            // 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
