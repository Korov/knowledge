package com.korov.cloud.rolemanager.provider.algorithmtest.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

/**
 * 希尔排序
 */
@Slf4j
public class ShellSort {

    @Test
    public void test() {
        Integer[] array = {13, 25, 8, 16, 48, 55, 16, 9, 83, 19};
        log.info(Arrays.toString(array));
        shellSort(array);
        log.info(Arrays.toString(array));
    }

    /**
     * 希尔排序方法
     *
     * @param array
     * @param <T>
     */
    public <T extends Comparable<? super T>> void shellSort(T[] array) {
        int i = 0;
        int j = 0;
        int gap = 0;
        T tmp;
        for (gap = array.length / 2; gap > 0; gap /= 2) {
            log.info(String.valueOf(gap));
            // 此部分与插入排序法相似
            for (i = gap; i < array.length; i++) {
                tmp = array[i];
                for (j = i; j >= gap && tmp.compareTo(array[j - gap]) < 0; j -= gap) {
                    array[j] = array[j - gap];
                    //log.info(Arrays.toString(array));
                }
                array[j] = tmp;
            }
        }
    }
}
