package com.korov.cloud.rolemanager.provider.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VoidTest {
    @Test
    public void test() {
        List<Integer> array = new ArrayList<>();
        array.add(12);
        array.add(45);
        Integer integer = array.remove(1);
        System.out.println("end");
    }
}
