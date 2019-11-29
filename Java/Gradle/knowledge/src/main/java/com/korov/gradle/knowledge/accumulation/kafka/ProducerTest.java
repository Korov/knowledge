package com.korov.gradle.knowledge.accumulation.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerTest {
    public static void main(String[] args) {
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
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>("mykafka", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }
}
