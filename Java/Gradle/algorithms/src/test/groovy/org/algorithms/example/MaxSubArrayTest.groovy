package org.algorithms.example

import org.algorithms.example.util.FileIterable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * @author zhu.lei* @date 2021-06-17 16:04
 */
class MaxSubArrayTest extends Specification {
    Logger logger = LoggerFactory.getLogger(MaxSubArrayTest.class)

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
            logger.info("cost:{}, sum:{}, left:{}, right:{}", end - start, result.getSum(), result.getLeft(), result.getRight())
        }

        then:
        notThrown(Exception)

    }
}
