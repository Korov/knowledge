package org.algorithms.example.solution

import spock.lang.Specification

class Solutions25Test extends Specification {
    def "test"() {
        given:
        Solutions25.ListNode listNode = new Solutions25.ListNode(1)
        Solutions25.ListNode currentNode = listNode
        currentNode.next = new Solutions25.ListNode(4)
        currentNode = currentNode.next
        currentNode.next = new Solutions25.ListNode(5)
        currentNode = currentNode.next
        currentNode.next = new Solutions25.ListNode(6)
        currentNode = currentNode.next
        currentNode.next = new Solutions25.ListNode(7)
        currentNode = currentNode.next
        currentNode.next = new Solutions25.ListNode(8)
        currentNode = currentNode.next
        currentNode.next = new Solutions25.ListNode(9)


        when:

        def result = Solutions25.reverseKGroup(listNode, 3)

        then:
        true
    }

    def "test1"() {
        given:
        Solutions25.ListNode listNode = new Solutions25.ListNode(1)
        Solutions25.ListNode currentNode = listNode
        currentNode.next = new Solutions25.ListNode(2)
        currentNode = currentNode.next
        currentNode.next = new Solutions25.ListNode(3)
        currentNode = currentNode.next
        currentNode.next = new Solutions25.ListNode(4)
        when:
        def result = Solutions25.reverseKGroup(listNode, 2)

        then:
        true
    }

    def "test2"() {
        given:
        Solutions25.ListNode listNode = new Solutions25.ListNode(1)
        Solutions25.ListNode currentNode = listNode
        currentNode.next = new Solutions25.ListNode(2)
        when:
        def result = Solutions25.reverseKGroup(listNode, 2)

        then:
        true
    }
}
