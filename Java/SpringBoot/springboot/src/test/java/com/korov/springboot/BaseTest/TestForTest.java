package com.korov.springboot.BaseTest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestForTest {
    @Test
    public void test() {
        List<List<Integer>> results = new ArrayList<>();
        results.add(new ArrayList<>());
        results.get(0).add(8);
        System.out.println(results.get(0).get(0));
    }

}
