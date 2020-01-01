package com.korov.gradle.knowledge.accumulation.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * @author macun 2015年11月19日 上午9:56:00
 */
public class AIOSocketClient {

    public static final int    PORT = 8082;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        // create a client
        SocketAddress address = new InetSocketAddress(HOST, PORT);
        ClientWrapper client = new ClientWrapper(address);
        // start client thread
        client.start();
        try {
            client.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        client.close();
    }

    public static class ClientWrapper extends Thread {

        AsynchronousSocketChannel client;
        Future<Void>              connectFuture;

        public ClientWrapper(SocketAddress server) throws IOException{
            // open a new socket channel and connect to the server
            System.out.println("Open client channel");
            client = AsynchronousSocketChannel.open();
            System.out.println("Connect to server");
            connectFuture = client.connect(server);
        }

        public void run() {
            System.out.println("client run.");
            // if the connect hasn't happened yet cancel it
            // if (!connectFuture.isDone()) {
            // connectFuture.cancel(true);
            // return;
            // }
            try {
                connectFuture.get();
            } catch (InterruptedException e1) {
                System.out.println("client connect error." + e1);
                return;

            } catch (ExecutionException e1) {
                System.out.println("client connect error." + e1);
                return;
            }

            try {
                // send a message to the server
                ByteBuffer message = ByteBuffer.wrap("hello server, i am Bruce.".getBytes());
                // wait for the response
                System.out.println("Sending message to the server...");
                Integer countBytes = client.write(message).get();
                System.out.println(countBytes);

                final ByteBuffer readBuffer = ByteBuffer.allocate(100);
                // Future<Integer> numberBytes = client.read(readBuffer);
                client.read(readBuffer, null, new CompletionHandler<Integer, Object>() {

                    @Override
                    public void completed(Integer result, Object attachment) {
                        System.out.println("Message received from server: " + new String(readBuffer.array()));
                        clearUp();
                    }

                    @Override
                    public void failed(Throwable e, Object attachment) {
                        System.err.println("Exception performing write");
                        e.printStackTrace();
                        clearUp();
                    }

                    private void clearUp() {
                        try {
                            client.close();
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                });

            } catch (InterruptedException e) {
                System.out.println(e);
            } catch (ExecutionException e) {
                System.out.println(e);
            }
        }

        public void close() throws IOException {
            client.close();
        }
    }
}
