package com.distributed.transaction.kafka.consumer;

import com.distributed.transaction.dao.TransactionLogMapper;
import com.distributed.transaction.model.TransactionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CheckConsumerTransactionLog {
    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Scheduled(fixedRate = 5000) // 每秒钟执行一次
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage() {
        List<TransactionLog> logs = transactionLogMapper.selectRecLog();
        for (TransactionLog transactionLog : logs) {
            switch (transactionLog.getTransactionType()){
                case "USER_CREATE":
                    log.info("处理创建用户事务");
                default:
                    log.info("未定义的事件");
            }
            transactionLog.setState("COMPLETE");
            transactionLog.setUpdateTime(new Date());
            transactionLogMapper.updateByPrimaryKey(transactionLog);
        }
    }
}
