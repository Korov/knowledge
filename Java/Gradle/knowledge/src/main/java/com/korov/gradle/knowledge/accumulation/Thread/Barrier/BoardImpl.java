package com.korov.gradle.knowledge.accumulation.Thread.Barrier;

public class BoardImpl implements Board {
    private int x;
    private int y;
    private int value;

    public BoardImpl(int x, int y) {
        setNewValue(x, y, getValue(x, y));
    }

    @Override
    public int getMaxX() {
        return 3;
    }

    @Override
    public int getMaxY() {
        return 3;
    }

    @Override
    public int getValue(int x, int y) {
        return x + y;
    }

    @Override
    public int setNewValue(int x, int y, int value) {
        this.x = x;
        this.y = y;
        return getValue(x, y);
    }

    @Override
    public void commitNewValues() {

    }

    @Override
    public boolean hasConverged() {
        return true;
    }

    @Override
    public void waitForConvergence() {

    }

    @Override
    public Board getSubBoard(int numPartitions, int index) {
        return null;
    }
}
