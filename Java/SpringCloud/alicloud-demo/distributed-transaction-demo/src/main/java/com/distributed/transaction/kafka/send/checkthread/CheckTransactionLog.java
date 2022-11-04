package com.distributed.transaction.kafka.send.checkthread;

import com.distributed.transaction.dao.TransactionLogMapper;
import com.distributed.transaction.model.TransactionLog;
import com.distributed.transaction.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService fixedPool = Executors.newFixedThreadPool(5);
        for (TransactionLog transactionLog : logs) {
            fixedPool.execute(new PublishRunner(transactionLogMapper, kafkaTemplate, transactionLog));
        }
    }

    class PublishRunner implements Runnable {
        private TransactionLogMapper transactionLogMapper;
        private KafkaTemplate<String, String> kafkaTemplate;
        private TransactionLog transactionLog;

        public PublishRunner(TransactionLogMapper transactionLogMapper, KafkaTemplate<String, String> kafkaTemplate, TransactionLog transactionLog) {
            this.transactionLogMapper = transactionLogMapper;
            this.kafkaTemplate = kafkaTemplate;
            this.transactionLog = transactionLog;
        }


        @Override
        public void run() {
            kafkaTemplate.send("transaction-demo", JSONUtil.objectToJson(transactionLog));
            transactionLog.setState("PUBLISHED");
            transactionLog.setUpdateTime(new Date());
            transactionLogMapper.updateByPrimaryKey(transactionLog);
        }
    }
}
