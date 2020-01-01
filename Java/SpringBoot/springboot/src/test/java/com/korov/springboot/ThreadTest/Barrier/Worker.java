package com.korov.springboot.ThreadTest.Barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {
    private final Board board;
    private final CyclicBarrier barrier;

    Worker(Board board, CyclicBarrier barrier) {
        this.board = board;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while (!board.hasConverged()) {
            for (int x = 0; x < board.getMaxX(); x++) {
                for (int y = 0; y < board.getMaxY(); y++) {
                    board.setNewValue(x, y, computeValues(x, y));
                }
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                return;
            }
        }
    }

    private int computeValues(int x, int y) {
        return x + y;
    }
}
