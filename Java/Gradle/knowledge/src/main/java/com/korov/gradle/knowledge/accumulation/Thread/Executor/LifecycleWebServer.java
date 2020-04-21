package com.korov.gradle.knowledge.accumulation.Thread.Executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LifecycleWebServer {
    private static final ExecutorService exec = Executors.newCachedThreadPool();
    private static final Logger log = Logger.getAnonymousLogger();

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                Socket conn = socket.accept();
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        LifecycleWebServer.handleRequest(conn);
                    }
                });
            } catch (IOException e) {
                if (!exec.isShutdown()) {
                    log.log(Level.WARNING, "task submission rejected", e);
                }
            }
        }
    }

    private static void stop() {
        exec.shutdown();
    }

    private static void handleRequest(Socket connection) {
        Request req = readRequest(connection);
        if (isShutdownRequest(req)) {
            log.info("is stoping");
            stop();
        } else {
            log.info("is resolving");
            dispatchRequest(req);
        }
    }

    interface Request {
    }

    private static Request readRequest(Socket s) {
        return null;
    }

    private static void dispatchRequest(Request r) {
    }

    private static boolean isShutdownRequest(Request r) {
        return false;
    }
}
