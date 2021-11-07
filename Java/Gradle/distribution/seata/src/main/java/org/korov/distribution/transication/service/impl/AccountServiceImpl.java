package org.korov.distribution.transication.service.impl;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.korov.distribution.transication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:20
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void debit(String userId, int money) {
        log.info("Account Service ... xid: " + RootContext.getXID());
        log.info("Deducting balance SQL: update account_tbl set money = money - {} where user_id = {}", money,
                userId);

        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", money, userId);
        log.info("Account Service End ... ");
    }
}
