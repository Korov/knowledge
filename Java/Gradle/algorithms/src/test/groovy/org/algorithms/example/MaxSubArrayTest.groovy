package org.algorithms.example

import org.algorithms.example.util.FileIterable
import org.junit.jupiter.api.Test
import spock.lang.Specification

/**
 * @author zhu.lei* @date 2021-06-17 16:04
 */
class MaxSubArrayTest extends Specification {
    def "test read data from file"() {
        given:
        FileIterable fileIterable = new FileIterable("src/test/resources/array.txt")
        Iterator<int[]> data = fileIterable.iterator()

        when:
        while (data.hasNext()) {
            int[] array = data.next()
            long start = System.nanoTime()
            MaxSubArray.Result result = MaxSubArray.getMaxSubArray(array, 0, array.length - 1)
            long end = System.nanoTime()
            System.out.println(String.format("cost:%s, sum:%s, left:%s, right:%s", end - start, result.getSum(), result.getLeft(), result.getRight()))
        }

        then:
        notThrown(Exception)

    }
}
