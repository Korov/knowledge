package org.algorithms.example.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Solutions718Test {

    @Test
    void findLength1() {
        int[] A = {1, 2, 3, 2, 1};
        int[] B = {3, 2, 1, 4, 7};
        Solutions718 solutions718 = new Solutions718();
        int result = solutions718.findLength(A, B);
        Assertions.assertEquals(3, result);
    }

    @Test
    void findLength2() {
        int[] A = new int[]{0, 0, 0, 0, 1};
        int[] B = new int[]{1, 0, 0, 0, 0};
        Solutions718 solutions718 = new Solutions718();
        int result = solutions718.findLength(A, B);
        Assertions.assertEquals(4, result);
    }

    @Test
    void findLength3() {
        int[] A = new int[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
        int[] B = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        Solutions718 solutions718 = new Solutions718();
        int result = solutions718.findLength(A, B);
        Assertions.assertEquals(9, result);
    }
}