package com.korov.gradle.knowledge.accumulation.Thread.ProviderConsumer;

import java.util.List;

public class Consumer implements Runnable {
    private final List<String> values;
    private final Object providerLock;
    private final Object consumerLock;

    Consumer(List<String> values, Object providerLock, Object consumerLock) {
        this.providerLock = providerLock;
        this.consumerLock = consumerLock;
        this.values = values;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (providerLock) {
                try {
                    if (values.size() == 0) {
                        providerLock.wait();
                    }
                    values.remove(values.size() - 1);
                    System.out.printf("consumer:%s remove a provider, Size:%s\n", Thread.currentThread().getName(), values.size());
                    providerLock.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
