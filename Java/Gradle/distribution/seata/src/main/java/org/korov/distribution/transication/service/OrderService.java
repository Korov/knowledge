package org.korov.distribution.transication.service;

import org.korov.distribution.transication.data.Order;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:02
 */
public interface OrderService {
    /**
     * 创建订单
     */
    Order create(String userId, String commodityCode, int orderCount);
}
