package com.korov.gradle.knowledge.service.impl;

import com.korov.gradle.knowledge.dao.NewfortuneDao;
import com.korov.gradle.knowledge.model.Newfortune;
import com.korov.gradle.knowledge.service.NewfortuneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NewfortuneServiceImpl implements NewfortuneService {
    @Autowired
    private NewfortuneDao dao;

    @Override
    public List<Newfortune> selectAll() {
        return dao.selectAll();
    }
}
