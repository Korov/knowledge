package com.korov.springboot.service.impl;

import com.korov.springboot.dao.IUserDao;
import com.korov.springboot.entity.UserEntity;
import com.korov.springboot.service.IAssistService;
import com.korov.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements IUserService {

    private static final String USER_ID = "userId";
    @Autowired
    private IUserDao userDao;

    @Autowired
    private IAssistService assistService;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return userDao.deleteByPrimaryKey(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(UserEntity record) {
        Map<String, Integer> countMap = new HashMap<>(1);
        int userCount = 0;
        List<List<Integer>> results = new ArrayList<>();
        results.add(new ArrayList<Integer>());
        addReleation(results.get(0), record);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(record.getLoginPassword());
        record.setLoginPassword(password);

        userCount = userDao.insert(record);
        return userCount;
    }

    @Override
    public int insertAll(List<UserEntity> records) {
        Map<String, Integer> countMap = new HashMap<>(1);
        int userCount = 0;

        List<List<Integer>> results = new ArrayList<>();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = null;
        for (UserEntity record : records) {
            password = passwordEncoder.encode(record.getLoginPassword());
            record.setLoginPassword(password);
        }

        userCount = userDao.insertAll(records);
        for (int i = 0; i < records.size(); i++) {
            results.add(new ArrayList<Integer>());
            addReleation(results.get(i), records.get(i));
        }
        return userCount;
    }

    @Override
    public UserEntity selectByPrimaryKey(Integer userId) {
        UserEntity entity = userDao.selectByPrimaryKey(userId);
        List<Integer> roleIds = null;
        List<Map<String, Integer>> results = assistService.selectUserRoleByUserId(userId);
        return entity;
    }

    @Override
    public UserEntity selectByUsername(String name) {
        return userDao.selectByUsername(name);
    }

    @Override
    public List<UserEntity> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public List<UserEntity> selectPage(Integer pageNum, Integer pageSize) {
        return userDao.selectPage(pageNum, pageSize);
    }

    @Override
    public int updateByPrimaryKey(UserEntity record) {
        return userDao.updateByPrimaryKey(record);
    }

    private void addReleation(List<Integer> results, UserEntity record) {
        int roleCount = 0;
        int groupCount = 0;
        int positionCount = 0;
        if (!Objects.isNull(record.getRoleIds())) {
            for (Integer id : record.getRoleIds()) {
                roleCount += assistService.insertUserRole(record.getUserId(), id);
            }
        }
        if (!Objects.isNull(record.getGroupIds())) {
            for (Integer id : record.getGroupIds()) {
                groupCount += assistService.insertUserGroup(record.getUserId(), id);
            }
        }
        if (!Objects.isNull(record.getPostIds())) {
            for (Integer id : record.getPostIds()) {
                positionCount += assistService.insertUserPosition(record.getUserId(), id);
            }
        }
        results.add(roleCount);
        results.add(groupCount);
        results.add(positionCount);
    }

}
