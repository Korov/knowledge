package org.algorithms.example.solution

import spock.lang.Specification

class Solutions856Test extends Specification {

    def "test"() {
        given:
        def string = "(()(()))" as String

        when:
        def result = Solutions856.scoreOfParentheses(string) as int

        then:
        result == 6
    }
}
