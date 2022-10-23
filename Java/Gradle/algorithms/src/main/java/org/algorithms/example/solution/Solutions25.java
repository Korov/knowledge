package org.algorithms.example.solution;

import java.util.ArrayList;
import java.util.List;

public class Solutions25 {
    public static ListNode reverseKGroup(ListNode list, int k) {
        int size = 0;
        List<ListNode[]> allArray = new ArrayList<>();
        ListNode[] array = new ListNode[k];
        allArray.add(array);
        while (list != null) {
            ListNode next = list.next;
            if (size < k) {
                array[size++] = list;
            } else {
                for (int i = 1; i < k; i++) {
                    array[i].next = array[i - 1];
                }
                if (allArray.size() > 1) {
                    allArray.get(allArray.size() - 2)[0].next = allArray.get(allArray.size() - 1)[k - 1];
                }
                size = 0;
                array = new ListNode[k];
                allArray.add(array);
                array[size++] = list;
            }
            list = next;
        }
        ListNode[] lastArray = allArray.get(allArray.size() - 1);
        if (lastArray[k - 1] != null) {
            for (int i = 1; i < k; i++) {
                lastArray[i].next = lastArray[i - 1];
            }
            if (allArray.size() > 1) {
                allArray.get(allArray.size() - 2)[0].next = allArray.get(allArray.size() - 1)[k - 1];
            }
            lastArray[0].next = null;
        } else {
            if (allArray.size() > 1) {
                allArray.get(allArray.size() - 2)[0].next = allArray.get(allArray.size() - 1)[0];
            }
        }
        ListNode result = null;
        if (allArray.size() == 1 && allArray.get(0)[k - 1] == null) {
            result = allArray.get(0)[0];
        } else {
            result = allArray.get(0)[k - 1];
        }
        return result;
    }

    /*ListNode tail = null;
        ListNode previous = list;
        ListNode current = previous.next;
        ListNode after = current.next;
        previous.next = null;
        while (after != null) {
            current.next = previous;
            previous = current;
            current = after;
            after = after.next;
        }
        current.next = previous;
        tail = current;
        return tail;*/

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
