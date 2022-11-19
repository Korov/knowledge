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

    private static final Integer DEFAULT_PORT = 8089;


    public static void initServer(Integer port) throws Exception {
        port = Optional.ofNullable(port).orElse(DEFAULT_PORT);
        socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(port));
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.printf("start server and listen port:%s%n", port);
    }


    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        client.register(selector, SelectionKey.OP_READ, buffer);
        System.out.printf("client:%s has connected%n", client.getRemoteAddress());
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
                    System.out.printf("receive data:%s, from client:%s%n", input, client.socket().getRemoteSocketAddress());
                }
                buffer.clear();
            } else {
                System.out.printf("receive empty data from client:%s%n", client.socket().getRemoteSocketAddress());
                break;
            }
        }
        key.attach("write");
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String response = "{\"code\":\"200\",\"version\",\"HTTP/1.1\"}";
        ByteBuffer headBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
        ByteBuffer buffer = ByteBuffer.wrap("Thanks!".getBytes(StandardCharsets.UTF_8));

        String type = Optional.ofNullable((String) key.attachment()).orElse("");
        try {
            channel.write(new ByteBuffer[]{headBuffer, buffer});
            System.out.printf("send respond type:%s to client:%s%n", type, channel.getRemoteAddress());
        } finally {
            channel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            initServer(DEFAULT_PORT);
            while (true) {
                while (selector.select(0) > 0) {
                    // 获取有事件发生的socket集合
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
        } finally {
            socketChannel.close();
            selector.close();
            System.out.printf("server channel and selector closed%n");
        }
    }
}
