package com.korov.springboot.annotation.ReadOnly;

import com.korov.springboot.config.DbContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Description
 * 然后写一个切面来切换数据使用哪种数据源，
 * 重写getOrder保证本切面优先级高于事务切面优先级，在启动类加上@EnableTransactionManagement(order = 10),为了代码如下：
 *
 * @author fxb
 * @date 2018-08-31
 */
@Aspect
@Component
public class ReadOnlyInterceptor implements Ordered {
    private static final Logger log = LoggerFactory.getLogger(ReadOnlyInterceptor.class);

    @Around("@annotation(readOnly)")
    public static Object setRead(ProceedingJoinPoint joinPoint, ReadOnly readOnly) throws Throwable {
        try {
            DbContextHolder.setDbType(DbContextHolder.SLAVE);
            return joinPoint.proceed();
        } finally {
            //清除DbType一方面为了避免内存泄漏，更重要的是避免对后续在本线程上执行的操作产生影响
            DbContextHolder.clearDbType();
            log.info("清除threadLocal");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
