package com.distributed.lock.mysql;

import com.distributed.lock.mysql.dao.TableLockMethodDao;
import com.distributed.lock.mysql.model.TableLockMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LockService {
    @Autowired
    private TableLockMethodDao dao;

    /**
     * 将过期的锁清楚掉
     */
    public void removeLocks() {
        List<TableLockMethod> locks = dao.selectAllLocked();
        for (TableLockMethod lock : locks) {
            long timeNow = System.currentTimeMillis();
            if ((timeNow - Long.parseLong(lock.getTimeBegin())) > Long.parseLong(lock.getTimeValid())) {
                lock.setState(2);
                dao.updateByPrimaryKeySelective(lock);
            }
        }
    }


    public void lock(String resources) {
        TableLockMethod lock = dao.selectByResources(resources);

        // 循环获取锁，一直到获取到该锁
        while (lock.getState().equals(1)) {
            lock = dao.selectByResources(resources);
        }
        lock.setState(1);
        lock.setTimeBegin(String.valueOf(System.currentTimeMillis()));
        lock.setTimeValid("2000");
        lock.setUpdateTime(new Date());
        lock.setDesc(Thread.currentThread().getName());
        dao.updateByPrimaryKey(lock);
    }

    public void unlock(String resources) {
        TableLockMethod lock = dao.selectByResources(resources);

        if (lock.getState().equals(2)) {
            System.out.println("线程未上锁");
        }
        if (!lock.getDesc().equals(Thread.currentThread().getName())) {
            System.out.println("当前线程未持有该锁");
        }

        lock.setState(2);
        lock.setTimeBegin("0");
        lock.setTimeValid("0");
        lock.setUpdateTime(new Date());
        dao.updateByPrimaryKey(lock);
    }
}
