package com.distributed.transaction.kafka.consumer;

import com.distributed.transaction.dao.TransactionLogMapper;
import com.distributed.transaction.dao.UserInfoMapper;
import com.distributed.transaction.model.TransactionLog;
import com.distributed.transaction.model.UserInfo;
import com.distributed.transaction.util.JSONUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoConsumerService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TransactionLogMapper transactionLogMapper;


    /**
     * 实现个人信息插入和事务日志信息一起插入的事务
     *
     * @param userInfo
     */
    @Transactional
    public void insertUser(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setId(userInfo.getId());
        transactionLog.setState("NEW");
        transactionLog.setTransactionType("USER_CREATE");
        transactionLog.setMessage(JSONUtil.objectToJson(userInfo));
        transactionLog.setTransactionProcess("NEW");
        transactionLog.setDesc(Thread.currentThread().getName());
        transactionLog.setUpdateTime(new Date());
        transactionLogMapper.insert(transactionLog);
    }

    @KafkaListener(id = "test1", topics = "transaction-demo", containerFactory = "kafkaListenerContainerFactory1")
    public void listen(ConsumerRecord<?, ?> record) throws InterruptedException {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            TransactionLog log = JSONUtil.jsonToObject((String) message, TransactionLog.class, JSONUtil.SNAKE_CASE_MAPPER);
            TransactionLog tempLog = transactionLogMapper.selectByPrimaryKey(log.getId());
            if (tempLog.getState().equals("PUBLISHED")) {
                log.setState("RECEIVED");
                log.setUpdateTime(new Date());
                transactionLogMapper.updateByPrimaryKey(log);
            }
        }
    }
}
