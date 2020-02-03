package com.korov.cloud.rolemanager.provider.algorithmtest.sort;

import com.korov.cloud.rolemanager.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 快速排序
 */
@Slf4j
public class QuickSort {
    private static final int CUTOFF = 3;

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(new Integer[]{1, 8, 4, 16, 25, 14, 33, 5, 14, 16, 85, 44, 75, 666, 254, 33, 11, 95});
        Integer[] array=new Integer[]{1, 8, 4, 16, 25, 14, 33, 5, 14, 16, 85, 44, 75, 666, 254, 33, 11, 95};
        log.info(list.toString());
        List<Integer> sortedList = quickSort(list);
        log.info(sortedList.toString());

        log.info(Arrays.toString(array));
        quickSort(array);
        log.info(Arrays.toString(array));
    }

    //自己写的垃圾排序
    public <T extends Comparable<? super T>> List<T> quickSort(List<T> list) {
        if (list == null || list.size() <= 1) {
            List<T> newList = new ArrayList<>(Constants.COLLECTIONSIZE);
            newList.addAll(list);
            return newList;
        }
        List<T> smallList = new ArrayList<>(Constants.COLLECTIONSIZE);
        List<T> sameList = new ArrayList<>(Constants.COLLECTIONSIZE);
        List<T> largeList = new ArrayList<>(Constants.COLLECTIONSIZE);
        T tmp = list.get(list.size() / 2);
        for (T item : list) {
            if (item.compareTo(tmp) < 0) {
                smallList.add(item);
            } else if (item.compareTo(tmp) > 0) {
                largeList.add(item);
            } else {
                sameList.add(item);
            }
        }
        List<T> newSmallList = quickSort(smallList);
        List<T> newLargeList = quickSort(largeList);

        List<T> newList = new ArrayList<>(Constants.COLLECTIONSIZE);
        newList.addAll(newSmallList);
        newList.addAll(sameList);
        newList.addAll(newLargeList);
        return newList;
    }

    public <T extends Comparable<? super T>> void quickSort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private <T extends Comparable<? super T>> void quickSort(T[] array, int left, int right) {
        //对于数组长度大于CUTOFF才进行快速排序，否则进行插入排序
        if (left + CUTOFF <= right) {
            T pivot = median3(array, left, right);
            int leftPointer = left;
            int rightPointer = right - 1;
            // 经历一遍循环将数组中的数据分为小于pivot和大于pivot两个部分
            for (; ; ) {
                while (array[++leftPointer].compareTo(pivot) < 0) {
                }
                while (array[--rightPointer].compareTo(pivot) > 0) {
                }
                if (leftPointer < rightPointer) {
                    swapReferences(array, leftPointer, rightPointer);
                } else {
                    break;
                }
            }
            // 将pivot由right - 1放置到合适位置
            swapReferences(array, leftPointer, right - 1);

            // 将左边和右边再分别进行快速排序
            quickSort(array, left, leftPointer - 1);
            quickSort(array, leftPointer + 1, right);
        } else {
            InsertionSort.insertionSort(array, left, right);
        }
    }

    /**
     * 1.对left，right和(left + right) / 2位置的数按照大小进行位置对调
     * 2.将对调完毕后将(left + right) / 2位置的数放到right - 1的位置
     * 3.返回right - 1位置的数作为中间的数
     * @param array
     * @param left
     * @param right
     * @param <T>
     * @return
     */
    private <T extends Comparable<? super T>> T median3(T[] array, int left, int right) {
        int center = (left + right) / 2;
        if (array[center].compareTo(array[left]) < 0) {
            swapReferences(array, left, center);
        }
        if (array[right].compareTo(array[left]) < 0) {
            swapReferences(array, left, right);
        }
        if (array[right].compareTo(array[center]) < 0) {
            swapReferences(array, center, right);
        }
        swapReferences(array, center, right - 1);
        return array[right - 1];
    }

    public static <T> void swapReferences(T[] array, int index1, int index2) {
        T tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}
