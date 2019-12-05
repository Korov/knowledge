package com.korov.gradle.knowledge.accumulation.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerTest {
    public static void main(String[] args) {
        String topicName = "mykafka1";
        String groupId = "mykafkagroup";
        Properties properties = new Properties();
        // 以下四项必须指定
        properties.put("bootstrap.servers", "192.168.106.143:9092");
        properties.put("group.id", groupId);//消费者所属群组
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("auto.offset.reset", "earliest");

        // 创建消费者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 订阅topic
        consumer.subscribe(Arrays.asList(topicName));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1000));
                records.forEach(record ->
                        System.out.printf("offset = %d, key = %s, value = %s\n",
                                record.offset(), record.key(), record.value()));
            }
        } finally {
            consumer.close();
        }
    }

}
