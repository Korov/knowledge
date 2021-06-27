package org.algorithms.example

import groovy.util.logging.Slf4j
import org.algorithms.example.util.FileIterable
import spock.lang.Specification

@Slf4j
class QuickSortTest extends Specification {
    def "test quick sort"() {
        given:
        def array = [73, 61, 16, 34, 50, 69, 82, 17, 83, 39] as int[]

        when:
        log.info("before: {}", array)
        QuickSort.quickSort(array, 0, array.length - 1)
        log.info("after: {}", array)

        then:
        Arrays.equals(array, [16, 17, 34, 39, 50, 61, 69, 73, 82, 83] as int[])
    }

    def "sort array from file"() {
        given:
        FileIterable fileIterable = new FileIterable("src/test/resources/array.txt")
        Iterator<int[]> data = fileIterable.iterator()

        when:
        while (data.hasNext()) {
            def array = data.next() as int[]
            def start = System.nanoTime()
            QuickSort.quickSort(array, 0, array.length - 1)
            def end = System.nanoTime()
            log.info("cost:{}, array: {}", end - start, array)
        }

        then:
        notThrown(Exception)
    }
}
