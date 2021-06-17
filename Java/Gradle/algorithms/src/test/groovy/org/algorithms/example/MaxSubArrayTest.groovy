package org.algorithms.example

import org.junit.jupiter.api.Test

/**
 * @author zhu.lei* @date 2021-06-17 16:04
 */
class MaxSubArrayTest {
    @Test
    void test() throws Exception {
        File file = new File("src/test/resources/array.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                int[] array = Arrays.stream(line.split(",")).mapToInt(Integer::valueOf).toArray();
                long start = System.nanoTime();
                MaxSubArray.Result result = MaxSubArray.getMaxSubArray(array, 0, array.length - 1);
                long end = System.nanoTime();
                System.out.println(String.format("cost:%s, sum:%s, left:%s, right:%s", end - start, result.getSum(), result.getLeft(), result.getRight()));

                long start1 = System.nanoTime();
                // MaxSubArray.Result result1 = MaxSubArray.maxSubArray(array, 0, array.length - 1);
                long end1 = System.nanoTime();
                // System.out.println(String.format("cost:%s, sum:%s, left:%s, right:%s", end1 - start1, result1.getSum(), result1.getLeft(), result1.getRight()));
            }
        }
    }
}
