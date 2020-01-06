package com.korov.springboot.config;

import com.korov.springboot.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * spring在开始进行数据库操作时会通过这个方法来决定使用哪个数据库，
 * 因此我们在这里调用上面DbContextHolder类的getDbType()方法获取当前操作类别,
 * 同时可进行读库的负载均衡，代码如下：
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

    @Value("${spring.datasource.num}")
    private int num;

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 根据DbContextHolder中设置的DbType来决定使用哪一个数据库。
     * DbType的值来自DataSourceConfig中routingDataSource设置的targetDataSources键值
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DbContextHolder.getDbType();
        if (typeKey == DbContextHolder.MASTER) {
            log.info("使用了写库");
            return typeKey;
        }
        //负载均衡，未实现。使用随机数决定使用哪个读库
        int sum = NumberUtil.getRandom(1, num);
        log.info("使用了读库{}", sum);
        return DbContextHolder.SLAVE + sum;
    }
}
