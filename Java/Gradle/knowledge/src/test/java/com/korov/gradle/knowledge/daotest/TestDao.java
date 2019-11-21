package com.korov.gradle.knowledge.daotest;

import com.korov.gradle.knowledge.KnowledgeApplicationTests;
import com.korov.gradle.knowledge.dao.DemoBakMapper;
import com.korov.gradle.knowledge.dao.DemoMapper;
import com.korov.gradle.knowledge.model.Demo;
import com.korov.gradle.knowledge.model.DemoBak;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDao extends KnowledgeApplicationTests {
    @Autowired
    private DemoMapper mapper;

    @Autowired
    private DemoBakMapper bakMapper;

    @Test
    public void test() {
        List<String> names = Stream.of(new String[]{"董事长", "CEO", "CFO", "首席执行官",
                "首席财务官", "财务负责人", "首席执行官(CEO)", "财务总监(财务负责人)", "财务总监(CFO)",
                "CFO(首席财务官)", "董事长(代理)", "总裁(首席执行官)", "执行董事长", "财务负责人(财务总监)",
                "CEO(首席执行官)", "现任董事长", "公司董事长", "联席董事长", "董事长(代行董事会秘书职责)", "首席财务官(CFO)",
                "首席财务官(财务负责人)", "副总经理(首席财务官)", "集团董事长", "轮值董事长"}).collect(Collectors.toList());
        List<Demo> list = mapper.selectAll();
        List<String> valueTemp;

        List<DemoBak> demoBaks = new ArrayList<>(1000);
        for (Demo demo : list) {
            valueTemp = Stream.of(demo.getJutizhiwu().split(",")).collect(Collectors.toList());
            valueTemp.retainAll(names);
            demo.setZhiwus(valueTemp);
            demoBaks.add(getBak(demo));
        }
        for (DemoBak demoBak : demoBaks) {
            bakMapper.insert(demoBak);
        }
        System.out.println("debug");
    }

    private static DemoBak getBak(Demo demo) {
        DemoBak demoBak = new DemoBak();
        demoBak.setCode(demo.getCode());
        demoBak.setDate(demo.getDate());
        demoBak.setName(demo.getName());
        demoBak.setZhiwuleibie(demo.getZhiwuleibie());
        demoBak.setJutizhiwu(String.join(",", demo.getZhiwus()));
        demoBak.setSex(demo.getSex());
        demoBak.setAge(demo.getAge());
        demoBak.setEdu(demo.getEdu());
        demoBak.setStartdate(demo.getStartdate());
        demoBak.setEnddate(demo.getEnddate());
        return demoBak;
    }
}
