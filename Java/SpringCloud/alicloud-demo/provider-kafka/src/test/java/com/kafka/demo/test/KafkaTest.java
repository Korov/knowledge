package com.kafka.demo.test;

import com.kafka.demo.ApplicationTests;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

public class KafkaTest extends ApplicationTests {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void send() {

        for (int i = 0; i < 50; i++) {
            try {
                kafkaTemplate.send("hello1", 0, "testKey", "value:" + String.valueOf(i)).get();
                kafkaTemplate.send("hello1", 1, "testKey", "value:" + String.valueOf(i));
                kafkaTemplate.send("hello1", 2, "testKey", "value:" + String.valueOf(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void addPartition() {
//        kafkaTemplate.partitionsFor("hello").add(new PartitionInfo());
        TopicPartition topicPartition1 = new TopicPartition("topic-name", 0);
        TopicPartition topicPartition2 = new TopicPartition("topic-name", 1);
    }

    @Test
    public void send1() {
        for (int i = 0; i < 50; i++) {
            kafkaTemplate.send("test-topic2", 3, "testKey1", "value:" + String.valueOf(i));
            kafkaTemplate.send("test-topic2", 4, "testKey2", "value:" + String.valueOf(i));
            kafkaTemplate.send("test-topic2", 5, "testKey3", "value:" + String.valueOf(i));
        }
    }
}
