package com.example.flux.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
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

                while (buffer.hasRemaining()){

                    client.write(buffer);
                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    System.out.println(String.format("接收到端口号为:%d的客户端发来的数据，值为：%s", client.socket().getPort(), new String(data)));
                }
                buffer.clear();
                // String response = "Thank you!";
                // ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                // byteBuffer.put(response.getBytes(StandardCharsets.UTF_8));
                // client.write(byteBuffer);
            }else if (0==count){
                break;
            }else {
                client.close();
                break;
            }
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
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
