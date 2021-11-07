package org.korov.distribution.transication.service.impl;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.korov.distribution.transication.dao.OrderDAO;
import org.korov.distribution.transication.data.Order;
import org.korov.distribution.transication.service.AccountService;
import org.korov.distribution.transication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:04
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountService accountService;

    @Override
    public Order create(String userId, String commodityCode, int orderCount) {
        log.info("Order Service Begin ... xid: " + RootContext.getXID());

        // 计算订单金额
        int orderMoney = calculate(commodityCode, orderCount);

        // 从账户余额扣款
        accountService.debit(userId, orderMoney);

        final Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info(
                "Order Service SQL: insert into order_tbl (user_id, commodity_code, count, money) values ({}, {}, {}, {})",
                userId, commodityCode, orderCount, orderMoney);

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pst = con.prepareStatement(
                        "insert into order_tbl (user_id, commodity_code, count, money) values (?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setObject(1, order.userId);
                pst.setObject(2, order.commodityCode);
                pst.setObject(3, order.count);
                pst.setObject(4, order.money);
                return pst;
            }
        }, keyHolder);

        order.id = keyHolder.getKey().longValue();

        log.info("Order Service End ... Created " + order);
        return order;
    }

    private int calculate(String commodityId, int orderCount) {
        return 200 * orderCount;
    }
}
