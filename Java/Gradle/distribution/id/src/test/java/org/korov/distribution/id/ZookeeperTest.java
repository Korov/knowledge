package org.korov.distribution.id;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author zhu.lei
 * @date 2021-10-23 18:00
 */
@Slf4j
public class ZookeeperTest {
    @Test
    void createTest() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(10000, 3));
        client.start();

        client.delete().forPath("/job-");
        String path = client.create().forPath("/job-", "test".getBytes(StandardCharsets.UTF_8));
        log.info("create path:{}", path);
        List<String> paths = client.getChildren().forPath(path);
        log.info("paths:{}", paths);
        byte[] data = client.getData().forPath(path);
        log.info("data:{} for path:{}", new String(data, StandardCharsets.UTF_8), path);
    }

    @Test
    void generateIdTest() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(10000, 3));
        client.start();
        for (long i = 0; i < 10000000000L; i++) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/test/job-", "test".getBytes(StandardCharsets.UTF_8));
        }
        String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/test/job-", "test".getBytes(StandardCharsets.UTF_8));
        log.info("create path:{}", path);
        List<String> paths = client.getChildren().forPath(path);
        log.info("paths:{}", paths);
        byte[] data = client.getData().forPath(path);
        log.info("data:{} for path:{}", new String(data, StandardCharsets.UTF_8), path);
    }

    @Test
    void watcherTest() {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(10000, 3));
        client.start();
        CuratorCache curatorCache = CuratorCache.build(client, "/test", CuratorCache.Options.SINGLE_NODE_CACHE);
        CuratorCacheListener listener = CuratorCacheListener.builder().forNodeCache(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                log.info("node changed");
            }
        }).build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }
}
