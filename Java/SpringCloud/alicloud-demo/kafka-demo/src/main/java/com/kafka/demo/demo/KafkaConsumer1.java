package com.kafka.demo.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer1 {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer1.class);

    /**
     * 会根据id生成一个消费者信息存储在kafka端
     *
     * @param record
     * @throws InterruptedException
     */
    @KafkaListener(id = "test1", topics = "test-topic2", containerFactory = "kafkaListenerContainerFactory1")
    public void listen(ConsumerRecord<?, ?> record) throws InterruptedException {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("listen consumer the message. record: {}, message: {}!", record, message);
        }
    }
}
