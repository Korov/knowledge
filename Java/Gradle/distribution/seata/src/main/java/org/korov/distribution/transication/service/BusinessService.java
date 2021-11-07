package org.korov.distribution.transication.service;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:03
 */
public interface BusinessService {
    /**
     * 采购
     */
    void purchase(String userId, String commodityCode, int orderCount);
}
