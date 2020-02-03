package com.korov.cloud.rolemanager.provider.algorithmtest.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

/**
 * 插入排序
 */
@Slf4j
public class InsertionSort {
    @Test
    public void test() {
        Integer[] array = {13, 25, 8, 16, 48, 55, 16, 9, 83, 19};
        log.info(Arrays.toString(array));
        insertionSort(array);
        log.info(Arrays.toString(array));
    }

    public static <T extends Comparable<? super T>> void insertionSort(T[] array) {
        insertionSort(array, 0, array.length-1);
    }

    /**
     * 插入排序方法
     * 运行时间O(n^2)，可以通过重写Compare实现不同的排序方式
     *
     * @param array
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void insertionSort(T[] array, int left, int right) {
        for (int pointer = left + 1; pointer <= right; pointer++) {
            T tmp = array[pointer];
            int j;
            for (j = pointer; j > left && tmp.compareTo(array[j - 1]) < 0; j--) {
                array[j] = array[j - 1];
            }
            array[j] = tmp;
        }
    }
}
