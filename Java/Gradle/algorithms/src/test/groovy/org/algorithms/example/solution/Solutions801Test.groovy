package org.algorithms.example.solution


import spock.lang.Specification

class Solutions801Test extends Specification {

    def "test"() {
        given:
        def num1 = [1, 3, 5, 4] as int[]
        def num2 = [1, 2, 3, 7] as int[]

        when:
        def result = Solutions801.minSwap(num1, num2)

        then:
        result == 1
    }

    def "test1"() {
        given:
        def num1 = [0, 1, 4, 6, 8] as int[]
        def num2 = [1, 2, 2, 7, 10] as int[]

        when:
        def result = Solutions801.minSwap(num1, num2)

        then:
        result == 1
    }

    def "test2"() {
        given:
        def num1 = [0, 7, 8, 10, 10, 11, 12, 13, 19, 18] as int[]
        def num2 = [4, 4, 5, 7, 11, 14, 15, 16, 17, 20] as int[]

        when:
        def result = Solutions801.minSwap(num1, num2)

        then:
        result == 4
    }
}