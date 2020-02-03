package com.korov.cloud.rolemanager.provider.algorithmtest.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

/**
 * 希尔排序
 * 基于插入排序，插入排序一次只能移动一个位置
 * 希尔排序一次能够移动的位置距离更大所以更快一点
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
     * 示例一个数组长度为10
     * 则第一趟排序gap为5，则会将0-5,1-6,2-7,3-8,4-9的位置进行排序
     * 第二趟gap为2，则将0-2,1-3。。。的位置进行排序
     * 直到gap为1
     *
     * @param array
     * @param <T>
     */
    public <T extends Comparable<? super T>> void shellSort(T[] array) {
        int pointer = 0;
        int rightPointer = 0;
        int gap = 0;
        T tmp;
        for (gap = array.length / 2; gap > 0; gap /= 2) {
            log.info(String.valueOf(gap));
            // 此部分与插入排序法相似
            for (pointer = gap; pointer < array.length; pointer++) {
                tmp = array[pointer];
                for (rightPointer = pointer; rightPointer >= gap && tmp.compareTo(array[rightPointer - gap]) < 0; rightPointer -= gap) {
                    array[rightPointer] = array[rightPointer - gap];
                    //log.info(Arrays.toString(array));
                }
                array[rightPointer] = tmp;
            }
        }
    }
}
