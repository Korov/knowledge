package org.korov.java;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.DeleteBuilder;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ZookeeperUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperUtil.class);

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", 5000, 3000, retryPolicy);
        client.start();

        ExistsBuilder existsBuilder = client.checkExists();
        if (existsBuilder.forPath("/curator-test") != null) {
            DeleteBuilder deleteBuilder = client.delete();
            deleteBuilder.forPath("/curator-test");
        }

        CreateBuilder createBuilder = client.create();
        createBuilder.forPath("/curator-test", "demo".getBytes(StandardCharsets.UTF_8));

        GetDataBuilder getDataBuilder = client.getData();
        logger.info(new String(getDataBuilder.forPath("/curator-test"), StandardCharsets.UTF_8));
        System.out.println(new String(getDataBuilder.forPath("/curator-test"), StandardCharsets.UTF_8));
        Stat stat = null;
        if ((stat = existsBuilder.forPath("/curator-test")) != null) {
            logger.info("version:{}", stat.getVersion());
            System.out.printf("version:%s%n", stat.getVersion());
        }

        client.close();
    }
}
