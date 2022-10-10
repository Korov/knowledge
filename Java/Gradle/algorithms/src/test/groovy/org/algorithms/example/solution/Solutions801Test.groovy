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
}