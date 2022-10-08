package org.algorithms.example.solution


import spock.lang.Specification

class Solutions870Test extends Specification {

    def "test"() {
        given:
        def num1 = [2, 7, 11, 15] as int[]
        def num2 = [1, 10, 4, 11] as int[]

        when:
        def result = Solutions870.advantageCount(num1, num2) as int[]

        then:
        result == [2, 11, 7, 15] as int[]
    }

    def "test1"() {
        given:
        def num1 = [2, 0, 4, 1, 2] as int[]
        def num2 = [1, 3, 0, 0, 2] as int[]

        when:
        def result = Solutions870.advantageCount(num1, num2) as int[]

        then:
        result == [2, 0, 2, 1, 4] as int[]
    }

    def "test2"() {
        given:
        def num1 = [15, 15, 4, 5, 0, 1, 7, 10, 3, 1, 10, 10, 8, 2, 3] as int[]
        def num2 = [4, 13, 14, 0, 14, 14, 12, 3, 15, 12, 2, 0, 6, 9, 0] as int[]

        when:
        def result = Solutions870.advantageCount(num1, num2) as int[]

        then:
        result == [2, 0, 2, 1, 4] as int[]
    }

    def "test3"() {
        given:
        def num1 = [9, 1, 2, 4, 5] as int[]
        def num2 = [6, 2, 9, 1, 4] as int[]

        when:
        def result = Solutions870.advantageCount(num1, num2) as int[]

        then:
        result == [2, 0, 2, 1, 4] as int[]
    }
}