package com.korov.gradle.knowledge.accumulation.thread.ProviderConsumer;

import java.util.List;

public class Provider implements Runnable {
    private final List<String> values;
    private final Object providerLock;
    private final Object consumerLock;

    Provider(List<String> values, Object providerLock, Object consumerLock) {
        this.providerLock = providerLock;
        this.consumerLock = consumerLock;
        this.values = values;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (providerLock) {
                try {
                    if (values.size() >= 50) {
                        providerLock.wait();
                    }
                    values.add("aaa");
                    System.out.printf("provider:%s add a provider, Size:%s\n", Thread.currentThread().getName(), values.size());
                    providerLock.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
