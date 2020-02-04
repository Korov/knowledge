package com.distributed.transaction.kafka.send.checkthread;

import com.alibaba.fastjson.JSON;
import com.distributed.transaction.dao.TransactionLogMapper;
import com.distributed.transaction.model.TransactionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CheckTransactionLog {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TransactionLogMapper transactionLogMapper;


    @Scheduled(fixedRate = 5000) // 每秒钟执行一次
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage() {
        List<TransactionLog> logs = transactionLogMapper.selectNewLog();
        for (TransactionLog transactionLog : logs) {
            log.info("My data: {}", JSON.toJSONString(transactionLog));
            kafkaTemplate.send("transaction-demo", JSON.toJSONString(transactionLog));
            transactionLog.setState("PUBLISHED");
            transactionLog.setUpdateTime(new Date());
            transactionLogMapper.updateByPrimaryKey(transactionLog);
        }
    }
}
