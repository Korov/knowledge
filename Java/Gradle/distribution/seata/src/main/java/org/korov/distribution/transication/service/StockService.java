package org.korov.distribution.transication.service;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:02
 */
public interface StockService {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
