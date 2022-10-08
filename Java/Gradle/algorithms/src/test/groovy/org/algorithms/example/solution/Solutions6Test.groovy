package org.algorithms.example.solution


import spock.lang.Specification

class Solutions6Test extends Specification {
    def "test mas sum"() {
        given:
        def string = "PAYPALISHIRING" as String

        when:
        def result = Solutions6.convert(string, 3) as String

        then:
        result == "PAHNAPLSIIGYIR"
    }

    def "test mas sum1"() {
        given:
        def string = "AB" as String

        when:
        def result = Solutions6.convert(string, 2) as String

        then:
        result == "AB"
    }
}