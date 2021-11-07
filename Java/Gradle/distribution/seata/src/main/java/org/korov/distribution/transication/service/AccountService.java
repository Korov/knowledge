package org.korov.distribution.transication.service;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:02
 */
public interface AccountService {
    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}
