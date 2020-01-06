package com.korov.springboot.annotation.ByMehtodName;

import com.korov.springboot.config.DbContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DetermineDatasourceByMethodName {
    /**
     * 设置切点，对于service包下select和get方法使用从数据库
     * 注释：@annotation和@within区别，@annotation的注解可以在方法级别上使用，@within只能在类级别上使用
     */
    @Pointcut("!@annotation(com.korov.springboot.annotation.ByMehtodName.Master) " +
            "&& (execution(* com.korov.springboot.service.determinebymethodname..*.select*(..)) " +
            "|| execution(* com.korov.springboot.service.determinebymethodname..*.get*(..)))")
    public void readPointcut() {

    }

    /**
     * 设置切点，对于service包下insert，add和update等方法使用主数据库
     */
    @Pointcut("@annotation(com.korov.springboot.annotation.ByMehtodName.Master) " +
            "|| execution(* com.korov.springboot.service.determinebymethodname..*.insert*(..)) " +
            "|| execution(* com.korov.springboot.service.determinebymethodname..*.add*(..)) " +
            "|| execution(* com.korov.springboot.service.determinebymethodname..*.update*(..)) " +
            "|| execution(* com.korov.springboot.service.determinebymethodname..*.edit*(..)) " +
            "|| execution(* com.korov.springboot.service.determinebymethodname..*.delete*(..)) " +
            "|| execution(* com.korov.springboot.service.determinebymethodname..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DbContextHolder.setDbType(DbContextHolder.SLAVE);
    }

    @Before("writePointcut()")
    public void write() {
        DbContextHolder.setDbType(DbContextHolder.MASTER);
    }

    /**
     * 另一种写法：if...else...  判断哪些需要读从数据库，其余的走主数据库
     */
//    @Before("execution(* com.cjs.example.service.impl.*.*(..))")
//    public void before(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
//            DBContextHolder.slave();
//        } else {
//            DBContextHolder.master();
//        }
//    }
}
