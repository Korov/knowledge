package org.algorithms.example.solution

import spock.lang.Specification

class Solutions1800Test extends Specification {

    def "test mas sum"() {
        given:
        def nums = [10, 20, 30, 5, 10, 50] as int[]

        when:
        def sum = Solutions1800.maxAscendingSum(nums) as int

        then:
        sum == 65
    }
}
