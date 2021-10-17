package org.korov.distribution.zk_lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

public class ExampleClientThatLocks {
    private final InterProcessMutex lock;
    private final FakeLimitedResource resource;
    private final String clientName;

    public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName) {
        this.resource = resource;
        this.clientName = clientName;
        lock = new InterProcessMutex(client, lockPath);
    }

    public void doWork(long time, TimeUnit unit) throws Exception {
        if (!lock.acquire(time, unit)) {
            throw new IllegalStateException(System.currentTimeMillis() + " " + clientName + " could not acquire the lock");
        }
        try {
            System.out.println(System.currentTimeMillis() + " " + clientName + " has the lock");
            resource.use();
        } finally {
            System.out.println(System.currentTimeMillis() + " " + clientName + " releasing the lock");
            lock.release(); // always release the lock in a finally block
        }
    }
}
