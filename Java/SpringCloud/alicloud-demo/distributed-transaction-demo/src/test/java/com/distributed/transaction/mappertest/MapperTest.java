package com.distributed.transaction.mappertest;

import com.alibaba.fastjson.JSON;
import com.distributed.transaction.ApplicationTests;
import com.distributed.transaction.dao.TransactionLogMapper;
import com.distributed.transaction.model.TransactionLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class MapperTest extends ApplicationTests {
    @Autowired
    private TransactionLogMapper mapper;

    @Test
    public void test1(){
        List<TransactionLog> logs=mapper.selectNewLog();
        mapper.selectByPrimaryKey(1);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Test
    public void sendMessage() {
        List<TransactionLog> logs = transactionLogMapper.selectNewLog();
        for (TransactionLog log:logs){
            kafkaTemplate.send("transaction-demo", JSON.toJSONString(log));
            log.setState("PUBLISHED");
            log.setUpdateTime(new Date());
            transactionLogMapper.updateByPrimaryKey(log);
        }
    }
}
