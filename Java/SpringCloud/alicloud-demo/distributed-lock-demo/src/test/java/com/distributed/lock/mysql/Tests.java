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

    @Autowired
    private LockService service;

    static class MyThread implements Runnable {
        private final String value;

        private LockService service;

        public MyThread(String value, LockService service) {
            this.value = value;
            this.service = service;
        }

        @Override
        public void run() {
            char[] values = value.toCharArray();
            service.lock("demo");
            try {
                for (int i = 0; i < values.length; i++) {
                    System.out.print(values[i]);
                }
                System.out.println("|||");
            } finally {
                service.unlock("demo");
            }

        }
    }

    @Test
    public void main() {
        TableLockMethod temp =dao.selectByResources("demo");
        if (temp!=null){
            dao.deleteByPrimaryKey(temp.getId());
        }
        TableLockMethod lock =new TableLockMethod();
        lock.setResources("demo");
        lock.setState(2);
        lock.setVersion(0);
        lock.setTimeBegin("0");
        lock.setTimeValid("0");
        lock.setDesc("");
        lock.setUpdateTime(new Date());
        dao.insert(lock);
        Thread thread1 = new Thread(new MyThread("zhangsan", service), "thread1");
        Thread thread2 = new Thread(new MyThread("lisi", service), "thread2");
        Thread thread3 = new Thread(new MyThread("wangwu", service), "thread3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
