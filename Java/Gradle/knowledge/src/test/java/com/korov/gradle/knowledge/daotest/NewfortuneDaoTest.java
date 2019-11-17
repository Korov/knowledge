package com.korov.gradle.knowledge.daotest;


import com.korov.gradle.knowledge.KnowledgeApplicationTests;
import com.korov.gradle.knowledge.dao.NewfortuneDao;
import com.korov.gradle.knowledge.model.Newfortune;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NewfortuneDaoTest extends KnowledgeApplicationTests {
    @Autowired
    private NewfortuneDao newfortuneDao;

    @Test
    public void test() {
        List<Newfortune> newfortunes = newfortuneDao.selectAll();
        System.out.println("test success!");
    }
}
