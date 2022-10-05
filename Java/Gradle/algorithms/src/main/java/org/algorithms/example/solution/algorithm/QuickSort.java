package org.algorithms.example.solution.algorithm;

/**
 * 讲解见数据结构与算法分析java语言描述7.7
 *
 * @author korov
 */
public class QuickSort<T extends Comparable<? super T>> {
    private static final int CUTOFF = 10;

    public void quickSort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public void quickSort(T[] array, int left, int right) {
        if (left + CUTOFF <= right) {
            T pivot = median3(array, left, right);
            int i = left, j = right - 1;
            for (; ; ) {
                while (array[++i].compareTo(pivot) < 0) {
                }
                while (array[--j].compareTo(pivot) > 0) {
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
    private T median3(T[] array, int left, int right) {
        int center = (left + right) / 2;
        if (array[left].compareTo(array[center]) > 0) {
            swap(array, left, center);
        }
        if (array[left].compareTo(array[right]) > 0) {
            swap(array, left, right);
        }
        if (array[center].compareTo(array[right]) > 0) {
            swap(array, center, right);
        }
        swap(array, center, right - 1);
        return array[right - 1];
    }

    private void swap(T[] array, int index1, int index2) {
        T value = array[index1];
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
    private void insertionSort(T[] array, int left, int right) {
        int j;
        for (int i = left + 1; i <= right; i++) {
            T value = array[i];
            for (j = i; j > left && value.compareTo(array[j - 1]) < 0; j--) {
                array[j] = array[j - 1];
            }
            array[j] = value;
        }
    }
}
