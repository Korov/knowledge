package org.algorithms.example.solution.algorithm;

/**
 * 堆排序
 *
 * @author korov
 */
public class HeapSort<T extends Comparable<? super T>> {
    public void heapSort(T[] array) {
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            percDown(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i);
            percDown(array, 0, i);
        }
    }

    private void percDown(T[] array, int i, int n) {
        int child;
        T tmp;

        for (tmp = array[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if (child != n - 1 && array[child].compareTo(array[child + 1]) < 0) {
                child++;
            }
            if (tmp.compareTo(array[child]) < 0) {
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

    private void swap(T[] array, int index1, int index2) {
        T value = array[index1];
        array[index1] = array[index2];
        array[index2] = value;
    }
}
