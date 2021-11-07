package org.korov.distribution.transication.service.impl;

import org.junit.jupiter.api.Test;
import org.korov.distribution.transication.DistributionTransication;
import org.korov.distribution.transication.DistributionTransicationTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhu.lei
 * @date 2021-11-07 15:33
 */
class BusinessServiceImplTest extends DistributionTransicationTest {
    @Autowired
    private BusinessServiceImpl service;

    @Test
    void purchase() {
        service.purchase("001", "001", 1);
    }
}