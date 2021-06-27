package org.algorithms.example

import groovy.util.logging.Slf4j
import org.algorithms.example.util.FileUtil
import spock.lang.Specification

/**
 * @author zhu.lei* @date 2021-06-17 16:04
 */
@Slf4j
class MaxSubArrayTest extends Specification {
    def "test read data from file"() {
        given:
        FileUtil fileIterable = new FileUtil("src/test/resources/array.txt")
        Iterator<int[]> data = fileIterable.iterator()

        when:
        while (data.hasNext()) {
            def array = data.next() as int[]
            def start = System.nanoTime()
            MaxSubArray.Result result = MaxSubArray.getMaxSubArray(array, 0, array.length - 1)
            def end = System.nanoTime()
            log.info("cost:{}, sum:{}, left:{}, right:{}", end - start, result.sum(), result.sum(), result.sum())
        }

        then:
        notThrown(Exception)
    }
}
