package org.algorithms.example.solution


import spock.lang.Specification

class Solutions10Test extends Specification {

    def "test"() {
        given:
        def s = "aa" as String
        def p = "a" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        !result
    }

    def "test1"() {
        given:
        def s = "aa" as String
        def p = "a*" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        result
    }

    def "test2"() {
        given:
        def s = "ab" as String
        def p = ".*" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        result
    }

    def "test3"() {
        given:
        def s = "aab" as String
        def p = "c*a*b" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        result
    }

    def "test4"() {
        given:
        def s = "mississippi" as String
        def p = "mis*is*p*." as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        !result
    }

    def "test5"() {
        given:
        def s = "mississippi" as String
        def p = "mis*is*ip*." as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        result
    }

    def "test6"() {
        given:
        def s = "aaa" as String
        def p = "ab*ac*a" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        result
    }

    def "test7"() {
        given:
        def s = "aaa" as String
        def p = "ab*a" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        !result
    }

    def "test8"() {
        given:
        def s = "aaa" as String
        def p = "ab*a*c*a" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        result
    }

    def "test9"() {
        given:
        def s = "a" as String
        def p = ".*..a*" as String

        when:
        def result = Solutions10.isMatch(s, p) as boolean

        then:
        !result
    }
}