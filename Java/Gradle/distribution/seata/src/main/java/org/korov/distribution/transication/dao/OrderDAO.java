package org.korov.distribution.transication.dao;

import org.korov.distribution.transication.data.Order;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:16
 */
public interface OrderDAO {
    Order insert(Order order);
}
