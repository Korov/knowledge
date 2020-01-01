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


    /**
     * 插入排序方法
     * 运行时间O(n^2)，可以通过重写Compare实现不同的排序方式
     *
     * @param array
     * @param <T>
     */
    public <T extends Comparable<? super T>> void insertionSort(T[] array) {
        int i = 0;
        int j = 0;
        for (i = 1; i < array.length; i++) {
            T tmp = array[i];
            for (j = i; j > 0 && tmp.compareTo(array[j - 1]) < 0; j--) {
                array[j] = array[j - 1];
            }
            array[j] = tmp;
        }
    }
}
