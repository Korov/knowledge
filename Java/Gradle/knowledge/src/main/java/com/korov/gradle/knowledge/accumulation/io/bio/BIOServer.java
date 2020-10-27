package com.korov.gradle.knowledge.accumulation.io.bio;

import com.korov.gradle.knowledge.utils.RegexUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    private static final ExecutorService executorPool = Executors.newFixedThreadPool(5);

    private static class Handler implements Runnable {
        private Socket clientSocket;

        public Handler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                //使用Reader获取从客户端发送过来的数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                char chars[] = new char[64];
                int len = reader.read(chars);
                StringBuffer sb = new StringBuffer();
                sb.append(new String(chars, 0, len));
                System.out.println("Get message from " + sb);
                // 使用Writer将数据写回给客户端
                String response = "Hello " + RegexUtil.findAll("No\\.\\d", sb.toString()).get(0) + "! I have get the message!";
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.write(response);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    // ignore on close
                }
            }
        }
    }

    public void serve(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        try {
            while (true) {
                long beforeTime = System.nanoTime();
                Socket clientSocket = socket.accept();
                System.out.println("Establish connection time: " + (System.nanoTime() - beforeTime) + " ns");
                // 每接收到一个请求就开启一个线程处理请求，并且该线程是阻塞的，直到结束关闭线程
                executorPool.execute(new Handler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        BIOServer server = new BIOServer();
        server.serve(8080);
    }
}
