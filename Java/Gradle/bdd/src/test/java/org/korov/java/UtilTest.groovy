package org.korov.java

import spock.lang.Specification

class UtilTest extends Specification {
    def "Length"() {
        expect:
        Util.length(a) == b

        where:
        a     | b
        "aaa" | 3
    }
}
