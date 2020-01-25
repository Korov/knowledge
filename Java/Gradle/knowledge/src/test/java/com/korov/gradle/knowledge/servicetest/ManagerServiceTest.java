package com.korov.gradle.knowledge.servicetest;

import com.korov.gradle.knowledge.KnowledgeApplicationTests;
import com.korov.gradle.knowledge.service.ManagersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerServiceTest extends KnowledgeApplicationTests {
    @Autowired
    private ManagersService managersService;

    @Test
    public void test1() {
        managersService.selectAll();
    }
}
