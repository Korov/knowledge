package com.kafka.demo.test;

import com.kafka.demo.ApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaTest extends ApplicationTests {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void send() {
        for (int i = 0; i < 5; i++) {
            kafkaTemplate.send("hello", "value:" + String.valueOf(i));
        }
    }
}
