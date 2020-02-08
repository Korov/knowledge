package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.compositepatterns;

import java.util.ArrayList;
import java.util.List;

public class Composite extends Component {
    // 构件容器
    private List<Component> components = new ArrayList<>();

    // 增加一个叶子构件或树枝构件
    public void add(Component component) {
        components.add(component);
    }

    // 删除一个叶子构件或树枝构件
    public void remove(Component component) {
        components.remove(component);
    }

    // 获得分支下的所有叶子构件和树枝构件
    public List<Component> getChildrens() {
        return components;
    }
}
