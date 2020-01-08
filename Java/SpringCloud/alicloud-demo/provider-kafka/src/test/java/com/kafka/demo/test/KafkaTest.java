package com.kafka.demo.test;

import com.kafka.demo.ApplicationTests;
import com.kafka.demo.demo.KafkaProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaTest extends ApplicationTests {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void test() {
        kafkaProducer.send();
    }
}
