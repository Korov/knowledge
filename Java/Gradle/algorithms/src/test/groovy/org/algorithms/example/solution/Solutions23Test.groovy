package org.algorithms.example.solution

import spock.lang.Specification

class Solutions23Test extends Specification {
    def "test"() {
        given:
        def lists = [new Solutions23.ListNode(1), new Solutions23.ListNode(1), new Solutions23.ListNode(2)] as Solutions23.ListNode[]
        Solutions23.ListNode node1 = new Solutions23.ListNode(1)
        lists[0] = node1
        node1.next = new Solutions23.ListNode(4)
        node1 = node1.next
        node1.next = new Solutions23.ListNode(5)

        Solutions23.ListNode node2 = new Solutions23.ListNode(1)
        lists[1] = node2
        node2.next = new Solutions23.ListNode(3)
        node2 = node2.next
        node2.next = new Solutions23.ListNode(4)

        Solutions23.ListNode node3 = new Solutions23.ListNode(2)
        lists[2] = node3
        node3.next = new Solutions23.ListNode(6)


        when:
        def result = Solutions23.mergeKLists(lists)

        then:
        true
    }
}
