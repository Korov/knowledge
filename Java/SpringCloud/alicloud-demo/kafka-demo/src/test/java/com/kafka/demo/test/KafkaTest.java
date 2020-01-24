package com.kafka.demo.test;

import com.kafka.demo.ApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaTest extends ApplicationTests {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void send1() {
        for (int i = 0; i < 50; i++) {
            kafkaTemplate.send("test-topic2", 3, "testKey1", "value:" + String.valueOf(i));
            kafkaTemplate.send("test-topic2", 4, "testKey2", "value:" + String.valueOf(i));
            kafkaTemplate.send("test-topic2", 5, "testKey3", "value:" + String.valueOf(i));
        }
    }
}
