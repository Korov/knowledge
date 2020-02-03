package com.distributed.lock.mysql;

import com.distributed.lock.ApplicationTests;
import com.distributed.lock.mysql.dao.TableLockMethodDao;
import com.distributed.lock.mysql.model.TableLockMethod;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Tests extends ApplicationTests {
    @Autowired
    private TableLockMethodDao dao;

    @Test
    public void test() {
        TableLockMethod model = new TableLockMethod();
        model.setResources("demo");
        model.setState(2);
        model.setVersion(0);
        model.setUpdateTime(new Date());
        dao.insert(model);
        System.out.println("aaa");
    }
}
