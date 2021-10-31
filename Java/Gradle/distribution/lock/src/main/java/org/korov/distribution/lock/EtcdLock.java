package org.korov.distribution.lock;

import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author zhu.lei
 * @date 2021-10-31 09:57
 */
public class EtcdLock {
    public static void main(String[] args) {
        // 创建Etcd客户端，Etcd服务端为单机模式
        Client client = Client.builder().endpoints("http://win.korov.org:2380").build();

        // 对于某共享资源制定的锁名
        String lockName = "/lock/mylock";

        // 模拟分布式场景下，多个进程“抢锁”
        for (int i = 0; i < 3; i++) {
            new MyThread(lockName, client).start();
        }
    }

    /**
     * 加锁方法，返回值为加锁操作中实际存储于Etcd中的key，即：lockName+UUID，
     * 根据返回的key，可删除存储于Etcd中的键值对，达到释放锁的目的。
     *
     * @param lockName
     * @param client
     * @param leaseId
     * @return
     */
    public static String lock(String lockName, Client client, long leaseId) {
        // lockName作为实际存储在Etcd的中的key的前缀，后缀是一个全局唯一的ID，从而确保：对于同一个锁，不同进程存储的key具有相同的前缀，不同的后缀
        StringBuffer strBufOfRealKey = new StringBuffer();
        strBufOfRealKey.append(lockName);
        strBufOfRealKey.append("/");
        strBufOfRealKey.append(UUID.randomUUID());

        // 加锁操作实际上是一个put操作，每一次put操作都会使revision增加1，因此，对于任何一次操作，这都是唯一的。(get,delete也一样)
        // 可以通过revision的大小确定进行抢锁操作的时序，先进行抢锁的，revision较小，后面依次增加。
        // 用于记录自己“抢锁”的Revision，初始值为0L
        long revisionOfMyself = 0L;

        KV kvClient = client.getKVClient();
        // lock，尝试加锁，加锁只关注key，value不为空即可。
        // 注意：这里没有考虑可靠性和重试机制，实际应用中应考虑put操作而重试
        try {
            PutResponse putResponse = kvClient
                    .put(ByteSequence.from(strBufOfRealKey.toString(), StandardCharsets.UTF_8),
                            ByteSequence.from("value", StandardCharsets.UTF_8),
                            PutOption.newBuilder().withLeaseId(leaseId).build())
                    .get(10, TimeUnit.SECONDS);

            // 获取自己加锁操作的Revision号
            revisionOfMyself = putResponse.getHeader().getRevision();
        } catch (InterruptedException | ExecutionException | TimeoutException e1) {
            System.out.println("[error]: lock operation failed:" + e1);
        }

        try {
            // lockName作为前缀，取出所有键值对，并且根据Revision进行升序排列，版本号小的在前
            List<KeyValue> kvList = kvClient.get(ByteSequence.from(lockName, StandardCharsets.UTF_8),
                            GetOption.newBuilder().withPrefix(ByteSequence.from(lockName, StandardCharsets.UTF_8))
                                    .withSortField(GetOption.SortTarget.MOD).build())
                    .get().getKvs();

            // 如果自己的版本号最小，则表明自己持有锁成功，否则进入监听流程，等待锁释放
            if (revisionOfMyself == kvList.get(0).getModRevision()) {
                System.out.println("[lock]: lock successfully. [revision]:" + revisionOfMyself);
                // 加锁成功，返回实际存储于Etcd中的key
                return strBufOfRealKey.toString();
            } else {
                // 记录自己加锁操作的前一个加锁操作的索引，因为只有前一个加锁操作完成并释放，自己才能获得锁
                int preIndex = 0;
                for (int index = 0; index < kvList.size(); index++) {
                    if (kvList.get(index).getModRevision() == revisionOfMyself) {
                        preIndex = index - 1;// 前一个加锁操作，故比自己的索引小1
                    }
                }
                // 根据索引，获得前一个加锁操作对应的key
                ByteSequence preKeyBS = kvList.get(preIndex).getKey();

                // 创建一个Watcher，用于监听前一个key
                long finalRevisionOfMyself = revisionOfMyself;
                Watch.Watcher watcher = client.getWatchClient().watch(preKeyBS, new Watch.Listener() {
                    @Override
                    public void onNext(WatchResponse response) {
                        System.out.println("[lock]: keep waiting until the lock is released.");
                        List<WatchEvent> eventlist = response.getEvents();
                        for (WatchEvent event : eventlist) {
                            // 如果监听到DELETE事件，说明前一个加锁操作完成并已经释放，自己获得锁，返回
                            if (event.getEventType().toString().equals("DELETE")) {
                                System.out.println("[lock]: lock successfully. [revision]:" + finalRevisionOfMyself);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("[error]: failed to listen key.");
                    }
                });
                WatchResponse res = null;
                // 监听前一个key，将处于阻塞状态，直到前一个key发生delete事件
                // 需要注意的是，一个key对应的事件不只有delete，不过，对于分布式锁来说，除了加锁就是释放锁
                // 因此，这里只要监听到事件，必然是delete事件或者key因租约过期而失效删除，结果都是锁被释放

            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("[error]: lock operation failed:" + e);
        }

        return strBufOfRealKey.toString();
    }

    /**
     * 释放锁方法，本质上就是删除实际存储于Etcd中的key
     *
     * @param lockName
     * @param client
     */
    public static void unLock(String realLockName, Client client) {
        try {
            client.getKVClient().delete(ByteSequence.from(realLockName, StandardCharsets.UTF_8)).get(10,
                    TimeUnit.SECONDS);
            System.out.println("[unLock]: unlock successfully.[lockName]:" + realLockName);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("[error]: unlock failed：" + e);
        }
    }

    /**
     * 自定义一个线程类，模拟分布式场景下多个进程 "抢锁"
     */
    public static class MyThread extends Thread {
        private final String lockName;
        private final Client client;

        MyThread(String lockName, Client client) {
            this.client = client;
            this.lockName = lockName;
        }

        @Override
        public void run() {
            // 创建一个租约，有效期15s
            Lease leaseClient = client.getLeaseClient();
            Long leaseId = null;
            try {
                leaseId = leaseClient.grant(15).get(10, TimeUnit.SECONDS).getID();
            } catch (InterruptedException | ExecutionException | TimeoutException e1) {
                System.out.println("[error]: create lease failed:" + e1);
                return;
            }

            // 创建一个定时任务作为“心跳”，保证等待锁释放期间，租约不失效；
            // 同时，一旦客户端发生故障，心跳便会中断，锁也会应租约过期而被动释放，避免死锁
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            // 续约心跳为12s，仅作为举例
            service.scheduleAtFixedRate(new KeepAliveTask(leaseClient, leaseId), 1, 12, TimeUnit.SECONDS);

            // 1\. try to lock
            String realLoclName = lock(lockName, client, leaseId);

            // 2\. to do something
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e2) {
                System.out.println("[error]:" + e2);
            }
            // 3\. unlock
            service.shutdown();// 关闭续约的定时任务
            unLock(realLoclName, client);
        }
    }

    /**
     * 在等待其它客户端释放锁期间，通过心跳续约，保证自己的key-value不会失效
     */
    public static class KeepAliveTask implements Runnable {
        private final Lease leaseClient;
        private final long leaseId;

        KeepAliveTask(Lease leaseClient, long leaseId) {
            this.leaseClient = leaseClient;
            this.leaseId = leaseId;
        }

        @Override
        public void run() {
            leaseClient.keepAliveOnce(leaseId);
        }
    }
}
