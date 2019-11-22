package com.korov.cloud.rolemanager.provider.algorithmtest.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class SortTest {

    @Test
    public void sortTest() {
        List<Integer> array = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000000; i++) {
            array.add(random.nextInt(100));
        }
        List<Integer> array1 = deepCopy(array);
        List<Integer> array2 = deepCopy(array);
        long startTime = 0L;
        long endTime = 0L;
        startTime = System.currentTimeMillis();
        //insertionSort(array1, 0, array.size() - 1);
        endTime = System.currentTimeMillis();
        log.info("insertionSort:{}", endTime - startTime);

        startTime = System.currentTimeMillis();
        quickSort(array1, 0, array.size() - 1);
        endTime = System.currentTimeMillis();
        log.info("quickSort:{}", endTime - startTime);

        startTime = System.currentTimeMillis();
        //mergeSort(array2);
        endTime = System.currentTimeMillis();
        log.info("mergeSort:{}", endTime - startTime);

        startTime = System.currentTimeMillis();
        heapSort(array2);
        endTime = System.currentTimeMillis();
        log.info("heapSort:{}", endTime - startTime);
    }

    public <T extends Comparable<? super T>> void insertionSort(List<T> array) {
        if (array == null || array.size() <= 1) {
            return;
        }
        T temp = null;
        int i = 0;
        int j = 0;
        for (i = 1; i < array.size(); i++) {
            temp = array.get(i);
            for (j = i; j > 0; j--) {
                if (array.get(j - 1).compareTo(temp) > 0) {
                    array.set(j, array.get(j - 1));
                } else {
                    break;
                }
            }
            array.set(j, temp);
        }
    }

    public <T extends Comparable<? super T>> void insertionSort(List<T> array, int left, int right) {
        if (array == null || array.size() <= 1 || left < 0 || left > right || right > array.size()) {
            return;
        }
        T temp = null;
        int i = 0;
        int j = 0;
        for (i = left + 1; i <= right; i++) {
            temp = array.get(i);
            for (j = i; j > 0; j--) {
                if (array.get(j - 1).compareTo(temp) > 0) {
                    array.set(j, array.get(j - 1));
                } else {
                    break;
                }
            }
            array.set(j, temp);
        }
    }

    public <T extends Comparable<? super T>> void quickSort(List<T> array) {
        if (array == null || array.size() <= 1) {
            return;
        }
        int baseIndex = array.size() / 2;
        T temp = array.get(baseIndex);
        List<T> leftArray = new ArrayList<>(10);
        List<T> middleArray = new ArrayList<>(10);
        List<T> rightArray = new ArrayList<>(10);
        for (T t : array) {
            if (t.compareTo(temp) < 0) {
                leftArray.add(t);
            } else if (t.compareTo(temp) == 0) {
                middleArray.add(t);
            } else {
                rightArray.add(t);
            }
        }
        quickSort(leftArray);
        quickSort(rightArray);
        array.clear();
        array.addAll(leftArray);
        array.addAll(middleArray);
        array.addAll(rightArray);
    }


    public <T extends Comparable<? super T>> void quickSort(List<T> array, int left, int right) {
        if (array == null || array.size() <= 1) {
            return;
        }
        if (left + 10 <= right) {
            //获取枢纽元素
            T pivot = middle3(array, left, right);
            int i = left;
            int j = right - 1;
            for (; ; ) {
                while (array.get(++i).compareTo(pivot) < 0) {
                }
                while (array.get(--j).compareTo(pivot) > 0) {
                }
                if (i < j) {
                    swapReferences(array, i, j);
                } else {
                    break;
                }
            }
            swapReferences(array, i, right - 1);
            quickSort(array, left, i - 1);
            quickSort(array, i + 1, right);
        } else {
            insertionSort(array, left, right);
        }
    }

    private <T extends Comparable<? super T>> T middle3(List<T> array, int left, int right) {
        int center = (left + right) / 2;
        if (array.get(left).compareTo(array.get(right)) > 0) {
            swapReferences(array, left, right);
        }
        if (array.get(center).compareTo(array.get(right)) > 0) {
            swapReferences(array, center, right);
        }
        if (array.get(left).compareTo(array.get(center)) > 0) {
            swapReferences(array, left, center);
        }
        swapReferences(array, center, right - 1);
        return array.get(right - 1);
    }

    private <T extends Comparable<? super T>> void swapReferences(List<T> array, int left, int right) {
        T temp = array.get(left);
        array.set(left, array.get(right));
        array.set(right, temp);
    }

    public <T extends Comparable<? super T>> void mergeSort(List<T> array) {
        List<T> tempArray = new ArrayList<>(array.size());
        mergeSort(array, tempArray, 0, array.size() - 1);
    }

    private <T extends Comparable<? super T>> void mergeSort(List<T> array, List<T> tempArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(array, tempArray, left, center);
            mergeSort(array, tempArray, center + 1, right);
            merge(array, tempArray, left, center + 1, right);
        }
    }

    private <T extends Comparable<? super T>> void merge(List<T> array, List<T> tempArray, int leftStart, int rightStart, int rightEnd) {
        int leftEnd = rightStart - 1;
        int arrayLength = rightEnd - leftStart + 1;
        while (leftStart <= leftEnd && rightStart <= rightEnd) {
            if (array.get(leftStart).compareTo(array.get(rightStart)) <= 0) {
                tempArray.add(array.get(leftStart++));
            } else {
                tempArray.add(array.get(rightStart++));
            }
        }

        while (leftStart <= leftEnd) {
            tempArray.add(array.get(leftStart++));
        }
        while (rightStart <= rightEnd) {
            tempArray.add(array.get(rightStart++));
        }
        for (int i = 0; i < arrayLength; i++, rightEnd--) {
            array.set(rightEnd, tempArray.remove(tempArray.size() - 1));
        }
    }

    public <T extends Comparable<? super T>> void heapSort(List<T> array) {
        int i = 0;
        for (i = array.size() / 2 - 1; i >= 0; i--) {
            precDown(array, i, array.size());
        }
        for (i = array.size() - 1; i > 0; i--) {
            swapReferences(array, 0, i);
            precDown(array, 0, i);
        }
    }

    private <T extends Comparable<? super T>> void precDown(List<T> array, int i, int length) {
        int child = 0;
        T temp = null;
        for (temp = array.get(i); getLeftChile(i) < length; i = child) {
            child = getLeftChile(i);
            if (child != length - 1 && array.get(child).compareTo(array.get(child + 1)) < 0) {
                child++;
            }
            if (temp.compareTo(array.get(child)) < 0) {
                array.set(i, array.get(child));
            } else {
                break;
            }
        }
        array.set(i, temp);
    }

    private int getLeftChile(int i) {
        return 2 * i + 1;
    }

    public <T> List<T> deepCopy(List<T> src) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.writeObject(src);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(byteIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        @SuppressWarnings("unchecked")
        List<T> dest = null;
        try {
            dest = (List<T>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dest;
    }
}
