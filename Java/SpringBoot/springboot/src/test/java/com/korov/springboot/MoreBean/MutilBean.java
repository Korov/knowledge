package com.korov.springboot.MoreBean;

import com.korov.springboot.BaseTest.MyTest;
import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.mutilbean.SingleBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 实现一个类多个beanID
 */
@Slf4j
public class MutilBean extends SpringbootApplicationTests {
    private Logger logger = LoggerFactory.getLogger(MutilBean.class);
    @Autowired
    @Qualifier(value = "singleBean1")
    private SingleBean bean;

    @Autowired
    @Qualifier(value = "singleBean2")
    private SingleBean bean2;

    @Test
    public void test() {
        logger.info("info    sfefwef");
        System.out.println(bean.getValue("values1"));
        System.out.println(bean2.getValue("values2"));
    }
}
