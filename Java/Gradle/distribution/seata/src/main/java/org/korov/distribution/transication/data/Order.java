package org.korov.distribution.transication.data;

import java.io.Serializable;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:15
 */
public class Order implements Serializable {
    /**
     * The Id.
     */
    public long id;
    /**
     * The User id.
     */
    public String userId;
    /**
     * The Commodity code.
     */
    public String commodityCode;
    /**
     * The Count.
     */
    public int count;
    /**
     * The Money.
     */
    public int money;

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId='" + userId + '\'' + ", commodityCode='" + commodityCode + '\''
                + ", count=" + count + ", money=" + money + '}';
    }
}
