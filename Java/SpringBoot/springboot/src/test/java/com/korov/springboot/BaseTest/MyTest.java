package com.korov.springboot.BaseTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.dao.ITestDao;
import com.korov.springboot.dao.IUserDao;
import com.korov.springboot.entity.UserEntity;
import com.korov.springboot.service.ITestService;
import com.korov.springboot.service.IUserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyTest extends SpringbootApplicationTests {

    private Logger logger = LoggerFactory.getLogger(MyTest.class);

    @Autowired
    private ITestDao testDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITestService testService;

    @Test
    public void myTest() {
        int number = 10;
        int size = 10 * number;
        List<List<UserEntity>> entities = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            entities.add(new ArrayList<UserEntity>(10000));
        }
        String loginNamePrefix = "Login Name:";
        String loginPassword = "test123";
        String nickNamePrefix = "Nick Name:";
        String email = "test@test.com";
        String phone = "12345678912";
        String sexMale = "male";
        String sexFemale = "female";
        String userType = "N";
        Integer status = 0;

        String loginName = null;
        String nickName = null;
        String sex = null;
        UserEntity entity = null;
        List<UserEntity> users = null;
        int listIndex = -1;
        for (int i = 0; i < size; i++) {
            loginName = "Login Name:" + i;
            nickName = "Nick Name:" + i;
            sex = ((int) (1 + Math.random() * 10) & 1) == 1 ? sexMale : sexFemale;
            entity = new UserEntity();
            entity.setLoginName(loginName);
            entity.setLoginPassword(loginPassword);
            entity.setNickName(nickName);
            entity.setEmail(email);
            entity.setPhone(phone);
            entity.setSex(sex);
            entity.setUserType(userType);
            entity.setStatus(status);
            if ((i % number) == 0) {
                listIndex++;
            }
            entities.get(listIndex).add(entity);
        }

        int result = 0;

        long startTime = 0;
        long endTime = 0;
        /* startTime = System.currentTimeMillis();
        for (UserEntity user : entities) {
            result += userService.insert(user);
        }
         endTime = System.currentTimeMillis();
        System.out.printf("插入了：%d条数据；\n花费了：%s；\n", result, (endTime - startTime));*/

        startTime = System.currentTimeMillis();
        for (List<UserEntity> list : entities) {
            result += userService.insertAll(list);
        }
        endTime = System.currentTimeMillis();
        System.out.printf("插入了：%d条数据；\n花费了：%s；\n", result, (endTime - startTime));

        startTime = System.currentTimeMillis();
        List<UserEntity> queryEntities = userService.selectAll();
        endTime = System.currentTimeMillis();
        System.out.printf("查询了：%d条数据；\n花费了：%s；\n", queryEntities.size(), (endTime - startTime));

        System.out.println("end");

    }
}
