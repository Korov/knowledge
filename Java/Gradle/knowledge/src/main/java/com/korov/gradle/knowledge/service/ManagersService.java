package com.korov.gradle.knowledge.service;


import com.korov.gradle.knowledge.model.Managers;

import java.util.List;

public interface ManagersService {
    List<Managers> selectAll();

    void insertAll(List<Managers> list);
}
