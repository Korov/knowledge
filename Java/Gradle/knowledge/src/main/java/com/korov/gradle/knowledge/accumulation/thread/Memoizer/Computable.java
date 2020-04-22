package com.korov.gradle.knowledge.accumulation.thread.Memoizer;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
