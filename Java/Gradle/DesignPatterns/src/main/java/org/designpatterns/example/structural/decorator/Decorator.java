package org.designpatterns.example.structural.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Decorator implements Work {
    private Work work;
    // 额外增加的功能被打包在这个list中
    private List functions = new ArrayList();

    // 在构造器中使用组合new方式，引入work对象
    public Decorator(Work work) {
        this.work = work;
        functions.add("挖坑");
        functions.add("钉木板");
    }

    @Override
    public void insert() {
        newMethod();
    }

    private void newMethod() {
        ListIterator iterator = functions.listIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + "正在进行");
        }
        work.insert();
    }
}
