package com.korov.gradle.knowledge.daotest;

import com.korov.gradle.knowledge.KnowledgeApplicationTests;
import com.korov.gradle.knowledge.dao.ManagersDao;
import com.korov.gradle.knowledge.model.Managers;
import com.korov.gradle.knowledge.utils.RegexUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManagerDaoTest extends KnowledgeApplicationTests {
    @Autowired
    private ManagersDao managersDao;

    @Test
    public void test() {
        List<String> names = Stream.of(new String[]{"董事长", "CEO", "CFO", "首席执行官",
                "首席财务官", "财务负责人", "首席执行官(CEO)", "财务总监(财务负责人)", "财务总监(CFO)",
                "CFO(首席财务官)", "董事长(代理)", "总裁(首席执行官)", "执行董事长", "财务负责人(财务总监)",
                "CEO(首席执行官)", "现任董事长", "公司董事长", "联席董事长", "董事长(代行董事会秘书职责)", "首席财务官(CFO)",
                "首席财务官(财务负责人)", "副总经理(首席财务官)", "集团董事长", "轮值董事长"}).collect(Collectors.toList());
        List<Managers> managers = managersDao.selectAll();
        Managers manager;
        for (Iterator<Managers> it = managers.iterator(); it.hasNext(); ) {
            manager = it.next();

            sortDuties(manager, names);

            if (!isMatch("首席|总监|长", manager)) {
                it.remove();
            } else if (isMatch("副", manager) && !isMatch("董事长|财务总监|财务负责人|首席", manager)) {
                it.remove();
            }
        }

        int insertCount = managersDao.insertAllResult(managers);
        System.out.println(insertCount);
    }

    private static boolean isMatch(String regex, Managers manager) {
        return RegexUtil.isPartMatch(regex, manager.getSpecificDuties());
    }

    private static void sortDuties(Managers manager, List<String> names) {
        List<String> duties = Stream.of(manager.getSpecificDuties().split(",")).collect(Collectors.toList());
        duties.retainAll(names);
        duties.sort(new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        manager.setSpecificDuties(String.join(",", duties));
    }
}
