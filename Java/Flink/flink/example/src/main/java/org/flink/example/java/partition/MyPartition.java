package org.flink.example.java.partition;

import org.apache.flink.api.common.functions.Partitioner;

/**
 * 创建自定义的分区规则，根据数字的奇偶性来分区
 *
 * @author korov
 * @date 2020/7/26
 */
public class MyPartition implements Partitioner<Long> {
    private static final long serialVersionUID = -6473055384022645141L;

    @Override
    public int partition(Long key, int numPartitions) {
        System.out.println("分区总数：" + numPartitions);
        if (key % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
