package org.algorithms.example;

/**
 * @author zhu.lei
 * @date 2021-06-17 15:48
 */
public class MaxSubArray {
    public static Result getMaxSubArray(int[] array, int left, int right) throws Exception {
        if (left > right) {
            throw new Exception("error input");
        }
        if (left == right) {
            return new Result(array, left, right);
        } else {
            int middle = (left + right) / 2;
            Result leftResult = getMaxSubArray(array, left, middle);
            Result rightResult = getMaxSubArray(array, middle + 1, right);
            Result middleResult = getMaxMiddleArray(array, left, right, middle);
            if (leftResult.sum >= middleResult.sum && leftResult.sum >= rightResult.sum) {
                return leftResult;
            } else if (middleResult.sum >= leftResult.sum && middleResult.sum >= rightResult.sum) {
                return middleResult;
            } else {
                return rightResult;
            }
        }
    }

    private static Result getMaxMiddleArray(int[] array, int left, int right, int middle) {
        int max = Integer.MIN_VALUE;
        int lefIndex = left;
        int rightIndex = right;
        int leftMax = 0;
        for (int i = middle; i >= left; i--) {
            leftMax += array[i];
            if (leftMax > max) {
                max = leftMax;
                lefIndex = i;
            }
        }
        max = Integer.MIN_VALUE;
        int rightMax = 0;
        for (int i = middle; i <= right; i++) {
            rightMax += array[i];
            if (rightMax > max) {
                max = rightMax;
                rightIndex = i;
            }
        }
        return new Result(array, lefIndex, rightIndex);
    }

    public static Result maxSubArray(int[] array, int left, int right) {
        if (left == right) {
            return new Result(array, left, right);
        } else {
            Result leftResult = maxSubArray(array, left, right - 1);
            Result rightResult = maxSubArray(array, left, left + 1);
            Result all = new Result(array, left, right);
            if (all.sum > leftResult.sum && all.sum > rightResult.sum) {
                return all;
            } else if (leftResult.sum >= rightResult.sum) {
                return leftResult;
            } else {
                return rightResult;
            }
        }
    }

    static class Result {
        public final Integer sum;
        public final Integer left;
        public final Integer right;

        public Result(int[] array, int lefIndex, int rightIndex) {
            this(sumArray(array), lefIndex, rightIndex);
        }

        public Result(int sum, int left, int right) {
            this.sum = sum;
            this.left = left;
            this.right = right;
        }

        private static int sumArray(int[] array) {
            int sum = 0;
            for (int value : array) {
                sum += value;
            }
            return sum;
        }
    }
}
