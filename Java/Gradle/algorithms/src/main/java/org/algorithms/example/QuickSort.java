package org.algorithms.example;

public class QuickSort {
    public static void quickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int q = partition(array, left, right);
        quickSort(array, left, q - 1);
        quickSort(array, q + 1, right);
    }

    /**
     * 需要优化获取中位数的方法，确保数据划分的更均衡
     * @param array
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] array, int left, int right) {
        int i = left - 1;
        for (int j = left; j <= right - 1; j++) {
            if (array[j] < array[right] && ++i < j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[right];
        array[right] = temp;
        return i + 1;
    }
}
