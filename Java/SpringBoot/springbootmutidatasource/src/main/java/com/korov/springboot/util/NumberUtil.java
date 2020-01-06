package com.korov.springboot.util;

/**
 * 在此处实现多个从数据库的负载均衡
 * 轮询算法
 * 随机算法
 * 加权轮询
 * 加权随机
 * 源地址哈希法
 *
 * @author fxb
 * @date 2018-09-07
 */
public class NumberUtil {

    public static int getRandom(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
