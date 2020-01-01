package com.korov.gradle.knowledge.accumulation.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Properties;

public class ProducerTest {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Properties properties = new Properties();
        // 以下三项必须指定
        properties.put("bootstrap.servers", "192.168.106.143:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        properties.put("acks", "-1");
        properties.put("retries", 3);
        properties.put("batch.size", 323804);
        properties.put("linger.ms", 10);
        properties.put("buffer.memory", 33554432);
        properties.put("max.block.ma", 3000);

        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 200; i++) {
            producer.send(new ProducerRecord<>("mykafka1", "t4_" + Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }

    private static void test2() {
        Properties properties = new Properties();
        // 以下三项必须指定
        properties.put("bootstrap.servers", "192.168.106.143:9092");

        properties.put("acks", "-1");
        properties.put("retries", 3);
        properties.put("batch.size", 323804);
        properties.put("linger.ms", 10);
        properties.put("buffer.memory", 33554432);
        properties.put("max.block.ma", 3000);
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<String> valueSerializer = new StringSerializer();

        Producer<String, String> producer = new KafkaProducer(properties, keySerializer, valueSerializer);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>("mykafka1", Integer.toString(i), Integer.toString(i + 1)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        // 消息发送成功
                    } else if (exception instanceof RetriableException) {
                        // 处理可重试瞬时异常
                    } else {
                        // 处理不可重试异常
                        producer.close(Duration.ofDays(0));
                    }
                }
            });
        }
        producer.close();
    }
}
