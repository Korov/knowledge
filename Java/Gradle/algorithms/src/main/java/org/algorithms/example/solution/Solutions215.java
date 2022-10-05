package org.algorithms.example.solution;

import java.util.Arrays;

/**
 * 在未排序的数组中找到第k个最大的元素。请注意，你需要找到的数数组排序后的第k个最大的元素，
 * 而不是第k个不同的元素。
 * 示例：
 * 输入: [3,2,1,5,6,4] 和 k = 2，输出：5
 * 说明：你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 */
public class Solutions215 {
    /**
     * 暴力解法
     *
     * @param nums
     * @param k
     * @return
     */
    public int method1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 使用快速排序
     *
     * @param nums
     * @param k
     * @return
     */
    public int method2(int[] nums, int k) {
        quickSort(nums);
        System.out.println(Arrays.toString(nums));
        return nums[nums.length - k];
    }


    public void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public void quickSort(int[] array, int left, int right) {
        if (left + 10 <= right) {
            int pivot = median3(array, left, right);
            int i = left, j = right - 1;
            for (; ; ) {
                while (array[++i] < pivot) {
                }
                while (array[--j] > pivot) {
                }
                if (i < j) {
                    swap(array, i, j);
                } else {
                    break;
                }
            }
            swap(array, i, right - 1);
            quickSort(array, left, i - 1);
            quickSort(array, i + 1, right);
        } else {
            // 数组较短的时候使用插入排序算法速度更快
            insertionSort(array, left, right);
        }
    }

    /**
     * 将数组的左中右三个位置的数据按照从小到大的顺序排好
     * 排好之后将中间位置的数据放到右边-1的位置并返回
     *
     * @param array
     * @param left
     * @param right
     * @return
     */
    private int median3(int[] array, int left, int right) {
        int center = (left + right) / 2;
        if (array[left] > array[center]) {
            swap(array, left, center);
        }
        if (array[left] > array[right]) {
            swap(array, left, right);
        }
        if (array[center] > array[right]) {
            swap(array, center, right);
        }
        swap(array, center, right - 1);
        return array[right - 1];
    }

    private void swap(int[] array, int index1, int index2) {
        int value = array[index1];
        array[index1] = array[index2];
        array[index2] = value;
    }

    /**
     * 插入排序
     *
     * @param array
     * @param left
     * @param right
     */
    private void insertionSort(int[] array, int left, int right) {
        int j;
        for (int i = left + 1; i <= right; i++) {
            int value = array[i];
            for (j = i; j > left && value < array[j - 1]; j--) {
                array[j] = array[j - 1];
            }
            array[j] = value;
        }
    }

    /**
     * 使用堆排序
     *
     * @param nums
     * @param k
     * @return
     */
    public int method3(int[] nums, int k) {
        heapSort(nums);
        return nums[nums.length - k];
    }

    public void heapSort(int[] array) {
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            percDown(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i);
            percDown(array, 0, i);
        }
    }

    private void percDown(int[] array, int i, int n) {
        int child;
        int tmp;

        for (tmp = array[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if (child != n - 1 && array[child] < array[child + 1]) {
                child++;
            }
            if (tmp < array[child]) {
                array[i] = array[child];
            } else {
                break;
            }
        }
        array[i] = tmp;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }
}
