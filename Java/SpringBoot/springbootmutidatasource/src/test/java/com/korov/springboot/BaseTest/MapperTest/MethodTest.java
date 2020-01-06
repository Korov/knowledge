package com.korov.springboot.BaseTest.MapperTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.service.determinebymethodname.AssistService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MethodTest extends SpringbootApplicationTests {
    @Autowired
    private AssistService assistService;

    @Test
    public void insertAndReadWithMasterTest() {
        assistService.insertAndRead(2, 1);
    }

    @Test
    public void selectFromSlaveTest() {
        assistService.selectUserRole(2, 1);
    }

    @Test
    public void insertAndReadWithSlaveTest() {
        assistService.selectAndInsert(2, 2);
    }

    @Test
    public void insertWithTransactionalTest() {
        assistService.insertWithTran(2, 2);
    }
}
