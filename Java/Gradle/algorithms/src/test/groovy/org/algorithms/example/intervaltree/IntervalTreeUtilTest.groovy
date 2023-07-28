package org.algorithms.example.intervaltree

import groovy.util.logging.Slf4j
import org.algorithms.example.redblacktree.Color
import org.junit.jupiter.api.Test

@Slf4j
class IntervalTreeUtilTest {

    @Test
    void rbInsert() {
        IntervalTreeNode<Integer, String> root = new IntervalTreeNode(Color.BLACK, new IntervalValue<Integer, String>(16, 21, "16,21"))

        root = IntervalTreeUtil.rbInsert(root, new IntervalTreeNode(null, new IntervalValue<Integer, String>(8, 17, "8,17")))
        root = IntervalTreeUtil.rbInsert(root, new IntervalTreeNode(null, new IntervalValue<Integer, String>(5, 10, "5,10")))
        root = IntervalTreeUtil.rbInsert(root, new IntervalTreeNode(null, new IntervalValue<Integer, String>(15, 23, "15,23")))
        root = IntervalTreeUtil.rbInsert(root, new IntervalTreeNode(null, new IntervalValue<Integer, String>(22, 30, "22,30")))
        root = IntervalTreeUtil.rbInsert(root, new IntervalTreeNode(null, new IntervalValue<Integer, String>(17, 19, "17,19")))
        root = IntervalTreeUtil.rbInsert(root, new IntervalTreeNode(null, new IntervalValue<Integer, String>(26, 26, "26,26")))
        log.info("debug")

        IntervalValue<Integer, String> value = new IntervalValue<>(23, 23, null)
        List<IntervalTreeNode<Integer, String>> result = IntervalTreeUtil.listTreeSearch(root, value)
        log.info("debug")
    }
}