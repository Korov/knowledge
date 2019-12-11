package com.korov.gradle.knowledge.accumulation.io.aio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AIOSocketServer {
    public static final int    PORT = 8082;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        new AIOSocketServer();
    }

    public AIOSocketServer() throws IOException, InterruptedException, ExecutionException {
        // open a server channel and bind to a free address, then accept a connection
        System.out.println("Open server channel");
        SocketAddress address = new InetSocketAddress(HOST, PORT);
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(address);
        System.out.println("Initiate accept");
        Future<AsynchronousSocketChannel> future = server.accept();

        // wait for the accept to finish
        AsynchronousSocketChannel worker = future.get();
        System.out.println("Accept completed");

        ByteBuffer readBuffer = ByteBuffer.allocate(100);
        try {
            // read a message from the client, timeout after 10 seconds
            worker.read(readBuffer).get(10, TimeUnit.SECONDS);
            System.out.println("Message received from client: " + new String(readBuffer.array()));

            // send a message to the client
            ByteBuffer message = ByteBuffer.wrap("hello client, i am Alice.".getBytes());
            worker.write(message);
        } catch (TimeoutException e) {
            System.out.println("Client didn't respond in time");
        }

        server.close();
    }
}
