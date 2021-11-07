package org.korov.distribution.transication.service.impl;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.korov.distribution.transication.service.BusinessService;
import org.korov.distribution.transication.service.OrderService;
import org.korov.distribution.transication.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:03
 */
@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private StockService storageService;
    @Autowired
    private OrderService orderService;

    private final Random random = new Random();

    /**
     * 采购
     */
    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "seata-demo-tx")
    public void purchase(String userId, String commodityCode, int orderCount) {
        log.info("purchase begin ... xid: " + RootContext.getXID());

        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);

        if (random.nextBoolean()) {
            throw new RuntimeException("random exception mock!");
        }
    }
}
