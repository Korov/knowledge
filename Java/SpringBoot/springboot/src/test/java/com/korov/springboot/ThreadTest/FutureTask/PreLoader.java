package com.korov.springboot.ThreadTest.FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用FutureTask预载稍后需要的数据
 */
public class PreLoader {
    ProductInfo loadProductInfo() {
        System.out.printf("ProductInfo is loading!\n");
        ProductInfo productInfo = new ProductionInfo(Thread.currentThread().getName());
        return productInfo;
    }

    private final FutureTask<ProductInfo> futureTask = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws Exception {
            return loadProductInfo();
        }
    });
    private final Thread thread = new Thread(futureTask);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws InterruptedException {
        try {
            return futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
