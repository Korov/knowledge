package com.korov.springboot.MoreBean;

import com.korov.springboot.SpringbootApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BeanTest extends SpringbootApplicationTests {

    @Autowired
    @Qualifier("Bean1")
    private Bean bean1;

    @Autowired
    @Qualifier("Bean2")
    private Bean bean2;

    @Test
    public void test() {
        bean1.run("aaa");
        bean2.run("aaa");

        System.out.println("aaa");
    }
}
