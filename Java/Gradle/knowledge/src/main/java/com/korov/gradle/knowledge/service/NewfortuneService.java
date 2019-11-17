package com.korov.gradle.knowledge.service;


import com.korov.gradle.knowledge.model.Newfortune;

import java.util.List;

public interface NewfortuneService {
    List<Newfortune> selectAll();
}
