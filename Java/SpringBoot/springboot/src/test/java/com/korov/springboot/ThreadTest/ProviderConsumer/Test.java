package com.korov.springboot.ThreadTest.ProviderConsumer;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static Object providerLock = new Object();
    public static Object consumerLock = new Object();
    public static List<String> values = new ArrayList<>(60);

    public static void main(String[] args) {
        Provider provider = new Provider(values, providerLock, consumerLock);
        Consumer consumer = new Consumer(values, providerLock, consumerLock);
        Thread provider1 = new Thread(provider, "Provider01");
        Thread provider2 = new Thread(provider, "Provider02");
        Thread provider3 = new Thread(provider, "Provider03");
        Thread provider4 = new Thread(provider, "Provider04");
        Thread consumer1 = new Thread(consumer, "Consumer01");
        Thread consumer2 = new Thread(consumer, "Consumer02");
        provider1.start();
        provider2.start();
        provider3.start();
        provider4.start();
        consumer1.start();
        consumer2.start();
    }
}
