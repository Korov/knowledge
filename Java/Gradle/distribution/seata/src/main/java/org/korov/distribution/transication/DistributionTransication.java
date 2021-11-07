package org.korov.distribution.transication;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.swing.*;

/**
 * @author zhu.lei
 * @date 2021-11-07 14:19
 */
@SpringBootApplication
@EnableTransactionManagement
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class DistributionTransication {
    public static void main(String[] args) {
        SpringApplication.run(DistributionTransication.class, args);
    }
}
