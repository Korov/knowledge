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
            if (leftResult.getSum() >= middleResult.getSum() && leftResult.getSum() >= rightResult.getSum()) {
                return leftResult;
            } else if (middleResult.getSum() >= leftResult.getSum() && middleResult.getSum() >= rightResult.getSum()) {
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
            if (all.getSum() > leftResult.getSum() && all.getSum() > rightResult.getSum()) {
                return all;
            } else if (leftResult.getSum() >= rightResult.getSum()) {
                return leftResult;
            } else {
                return rightResult;
            }
        }
    }

    static class Result {
        int sum;
        int left;
        int right;

        public Result() {
        }

        public Result(int[] array, int left, int right) {
            this.left = left;
            this.right = right;
            this.sum = 0;
            for (int i = left; i <= right; i++) {
                sum += array[i];
            }
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }
    }
}
