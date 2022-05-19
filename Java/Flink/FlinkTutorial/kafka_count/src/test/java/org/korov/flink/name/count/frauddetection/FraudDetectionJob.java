package org.korov.flink.name.count.frauddetection;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;
import org.korov.flink.name.count.frauddetection.common.Alert;
import org.korov.flink.name.count.frauddetection.common.Transaction;
import org.korov.flink.name.count.frauddetection.sink.AlertSink;
import org.korov.flink.name.count.frauddetection.source.TransactionSource;

/**
 * @author zhu.lei
 * @date 2022-05-19 23:26
 */
@Slf4j
public class FraudDetectionJob {
    @Test
    public void test() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Transaction> transactions = env
                .addSource(new TransactionSource())
                .name("transactions");

        DataStream<Alert> alerts = transactions
                // 按照用户id对数据进行分流，确保同一个task处理同一个key的所有数据
                .keyBy(Transaction::getAccountId)
                // 每个事件都会调用FraudDetector中的processElement函数
                .process(new FraudDetector())
                .name("fraud-detector");

        alerts.addSink(new AlertSink())
                .name("send-alerts");

        env.execute("Fraud Detection");
    }
}
