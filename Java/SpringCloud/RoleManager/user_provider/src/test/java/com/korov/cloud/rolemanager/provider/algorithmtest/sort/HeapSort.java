package com.korov.cloud.rolemanager.provider.algorithmtest.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;

/**
 * 堆排序
 */
@Slf4j
public class HeapSort {
    @Test
    public void test() {
        int length = 10000000;
        Random random = new Random();
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(100);
        }
        Integer[] array1 = {142, 543, 123, 65, 453, 879, 572, 434, 111, 242, 811, 102};
        long startTime = 0L;
        long endTime = 0L;
        startTime = System.currentTimeMillis();
        heapSort(array1, 0, array1.length - 1);
        endTime = System.currentTimeMillis();
        log.info("quickSort:{}", endTime - startTime);
    }

    /**
     * 堆排序
     * 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)
     * 堆是具有以下性质的完全二叉树：
     * 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
     * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆
     *
     * @param array
     * @param <T>
     */
    public <T extends Comparable<? super T>> void heapSort(T[] array) {
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            precDown(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            swapReferences(array, 0, i);
            precDown(array, 0, i);
        }
    }

    public <T extends Comparable<? super T>> void heapSort(T[] array, int left, int right) {

        for (int i = (right - left) / 2 - 1 + left; i >= left; i--) {
            precDown(array, i, left, right);
        }
        for (int i = right; i > left; i--) {
            swapReferences(array, left, i);
            precDown(array, left, left, i);
        }
    }

    private <T extends Comparable<? super T>> void swapReferences(T[] array, int left, int right) {
        T temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    private <T extends Comparable<? super T>> void precDown(T[] array, int i, int length) {
        T temp = null;
        int child = 0;
        for (temp = array[i]; getLeftChild(i) < length; i = child) {
            child = getLeftChild(i);
            if (child != length - 1 && array[child].compareTo(array[child + 1]) < 0) {
                child++;
            }
            if (temp.compareTo(array[child]) < 0) {
                array[i] = array[child];
            } else {
                break;
            }
        }
        array[i] = temp;
    }

    private <T extends Comparable<? super T>> void precDown(T[] array, int i, int left, int length) {
        T temp = null;
        int child = 0;
        for (temp = array[i]; getLeftChild(i, left) < length; i = child) {
            child = getLeftChild(i, left);
            if (child != length - 1 && array[child].compareTo(array[child + 1]) < 0) {
                child++;
            }
            if (temp.compareTo(array[child]) < 0) {
                array[i] = array[child];
            } else {
                break;
            }
        }
        array[i] = temp;
    }

    private int getLeftChild(int i) {
        return 2 * i + 1;
    }

    private int getLeftChild(int i, int left) {
        return 2 * i - left + 1;
    }
}
