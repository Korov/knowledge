package com.korov.springboot.ThreadTest.ProviderConsumer;

import java.util.List;

public class Provider implements Runnable {
    private List<String> values;
    private Object providerLock;
    private Object consumerLock;

    public Provider(List<String> values, Object providerLock, Object consumerLock) {
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
