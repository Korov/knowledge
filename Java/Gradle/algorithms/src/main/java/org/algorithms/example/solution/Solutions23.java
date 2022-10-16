package org.algorithms.example.solution;

public class Solutions23 {
    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode temp = new ListNode();
        ListNode result = temp;
        while (true) {
            int minIndex = -1;
            for (int i = 0; i < lists.length; i++) {
                ListNode node = lists[i];
                if (node == null) {
                    continue;
                }
                if (minIndex == -1) {
                    minIndex = i;
                } else if (node.val < lists[minIndex].val) {
                    minIndex = i;
                }
            }
            if (minIndex == -1) {
                break;
            }
            temp.next = new ListNode(lists[minIndex].val);
            temp = temp.next;
            lists[minIndex] = lists[minIndex].next;
        }
        return result.next;
    }

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
