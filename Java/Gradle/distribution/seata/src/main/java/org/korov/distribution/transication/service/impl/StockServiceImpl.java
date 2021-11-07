package org.korov.distribution.transication.service.impl;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.korov.distribution.transication.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:22
 */
@Slf4j
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deduct(String commodityCode, int count) {
        log.info("Stock Service Begin ... xid: " + RootContext.getXID());
        log.info("Deducting inventory SQL: update stock_tbl set count = count - {} where commodity_code = {}", count,
                commodityCode);

        jdbcTemplate.update("update stock_tbl set count = count - ? where commodity_code = ?",
                count, commodityCode);
        log.info("Stock Service End ... ");
    }
}
