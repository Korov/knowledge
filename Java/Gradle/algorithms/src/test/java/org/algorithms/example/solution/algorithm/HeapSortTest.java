package org.algorithms.example.solution.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class HeapSortTest {

    @Test
    void heapSort() {
        Integer[] array = new Integer[]{3, 4, 6, 77, 8, 1, 9, 4, 8, 2};
        HeapSort<Integer> sort = new HeapSort<>();
        sort.heapSort(array);
        System.out.println(Arrays.toString(array));
    }
}