package com.distributed.transaction.kafka.send.transaction;

import com.distributed.transaction.dao.TransactionLogMapper;
import com.distributed.transaction.dao.UserInfoMapper;
import com.distributed.transaction.model.TransactionLog;
import com.distributed.transaction.model.UserInfo;
import com.distributed.transaction.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TransactionLogMapper transactionLogMapper;


    /**
     * 实现个人信息插入和事务日志信息一起插入的事务
     * @param userInfo
     */
    @Transactional
    public void insertUser(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
        TransactionLog transactionLog=new TransactionLog();
        transactionLog.setId(userInfo.getId());
        transactionLog.setState("NEW");
        transactionLog.setTransactionType("USER_CREATE");
        transactionLog.setMessage(JSONUtil.objectToJson(userInfo));
        transactionLog.setTransactionProcess("NEW");
        transactionLog.setDesc(Thread.currentThread().getName());
        transactionLog.setUpdateTime(new Date());
        transactionLogMapper.insert(transactionLog);
    }
}
