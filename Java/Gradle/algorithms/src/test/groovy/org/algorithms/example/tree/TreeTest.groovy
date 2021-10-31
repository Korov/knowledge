package org.algorithms.example.tree

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test

@Slf4j
class TreeTest {
    void preOrder(Node<String> root) {
        log.info(root.getValue())
        if (root.getLeft() != null) {
            preOrder(root.getLeft())
        }
        if (root.getRight() != null) {
            preOrder(root.getRight())
        }
    }

    void postOrder(Node<String> root) {
        if (root.getLeft() != null) {
            postOrder(root.getLeft())
        }
        if (root.getRight() != null) {
            postOrder(root.getRight())
        }
        log.info(root.getValue())
    }

    void middleOrder(Node<String> root) {
        if (root.getLeft() != null) {
            middleOrder(root.getLeft())
        }
        log.info(root.getValue())
        if (root.getRight() != null) {
            middleOrder(root.getRight())
        }

    }

    class Node<K> {
        K value
        Node<K> left
        Node<K> right

        Node() {
        }

        Node(K value) {
            this.value = value
        }
    }

    @Test
    void printTree() {
        /**
         *      A
         *    B    C
         *  D     E F
         * G H
         */
        Node<String> root = null
        Node<String> top = new Node<>("D")
        Node<String> left = new Node<>("G")
        Node<String> right = new Node<>("H")
        top.left = left
        top.right = right
        left = top
        top = new Node<>("B")
        top.left = left
        left = top
        top = new Node<>("A")
        root = top
        right = new Node<>("C")
        top.left = left
        top.right = right
        top = right
        left = new Node<>("E")
        right = new Node<>("F")
        top.left = left
        top.right = right
        top = left
        right = new Node<>("I")
        top.right = right


        log.info("pre========================")
        // ABDGHCEIF
        preOrder(root)

        log.info("post=======================")
        // GHDBIEFCA
        postOrder(root)

        log.info("middle=====================")
        // GHDBIEFCA
        middleOrder(root)
    }
}
