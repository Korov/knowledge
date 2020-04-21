package com.korov.gradle.knowledge.accumulation.Thread.Memoizer;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
