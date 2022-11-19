package com.example.flux.nio;

import com.example.flux.httpd.Headers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class SingleThreadMultiplexingNIOTest {
    private static ServerSocketChannel socketChannel = null;
    private static Selector selector = null;


    public static void initServer() throws Exception {
        socketChannel = ServerSocketChannel.open();

        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(8089));
        selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }


    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        client.register(selector, SelectionKey.OP_READ, buffer);
        System.out.println("端口号为：" + client.getRemoteAddress() + "的客户端已连接。。。");
    }


    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        while (true) {
            int count = client.read(buffer);
            if (count > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    client.write(buffer);
                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    String input = new String(data, StandardCharsets.UTF_8);
                    System.out.printf("接收到端口号为:%d的客户端发来的数据，值为：%s%n", client.socket().getPort(), input);
                }
                buffer.clear();
            } else {
                break;
            }
        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String response = "{\"code\":\"200\",\"version\",\"HTTP/1.1\"}";
        ByteBuffer headBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
        ByteBuffer buffer = ByteBuffer.wrap("Thanks!".getBytes(StandardCharsets.UTF_8));
        try {
            channel.write(new ByteBuffer[]{headBuffer, buffer});
        } finally {
            channel.close();
        }
    }

    public static void main(String[] args) {
        try {
            initServer();
            while (true) {
                while (selector.select(0) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        } else if (key.isReadable()) {
                            handleRead(key);
                            key.interestOps(SelectionKey.OP_WRITE);
                        } else if (key.isWritable()) {
                            handleWrite(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
