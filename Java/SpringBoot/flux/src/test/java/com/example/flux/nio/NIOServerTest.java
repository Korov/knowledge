package com.example.flux.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NIOServerTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<SocketChannel> clientList = new ArrayList<>();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8089));

        serverSocketChannel.configureBlocking(false);
        while (true) {
            Thread.sleep(2000);
            SocketChannel client = serverSocketChannel.accept();
            if (null == client) {
                System.out.printf("没有客户端来连接，当前连接数量：%s%n", clientList.size());
            } else {

                client.configureBlocking(false);
                System.out.println(String.format("端口为:%d的客户端已经连接成功。。。", client.socket().getPort()));
                clientList.add(client);
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
            Iterator<SocketChannel> iterator = clientList.iterator();
            while (iterator.hasNext()) {
                SocketChannel clientItem = iterator.next();
                if (!clientItem.isConnected()) {
                    System.out.printf("端口号为:%d的客户端已断开%n", clientItem.socket().getPort());
                    iterator.remove();
                } else {
                    int count = clientItem.read(byteBuffer);
                    if (count > 0) {
                        byteBuffer.flip();
                        byte[] buffer = new byte[byteBuffer.limit()];
                        byteBuffer.get(buffer);
                        String request = new String(buffer);
                        System.out.printf("接收到端口号为:%d的客户端发来的数据，值为：%s%n", clientItem.socket().getPort(), request);
                        byteBuffer.clear();
                    }
                }
            }
        }
    }
}
