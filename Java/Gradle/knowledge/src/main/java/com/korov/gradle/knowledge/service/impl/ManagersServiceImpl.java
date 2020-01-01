package com.korov.gradle.knowledge.service.impl;


import com.korov.gradle.knowledge.dao.ManagersDao;
import com.korov.gradle.knowledge.model.Managers;
import com.korov.gradle.knowledge.service.ManagersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ManagersServiceImpl implements ManagersService {
    @Autowired
    private ManagersDao dao;

    @Override
    public List<Managers> selectAll() {
        return dao.selectAll();
    }
}
