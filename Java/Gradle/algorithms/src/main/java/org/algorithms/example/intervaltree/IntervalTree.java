package org.algorithms.example.intervaltree;

import org.algorithms.example.redblacktree.Color;
import org.algorithms.example.redblacktree.RedBlackNode;

/**
 * @author zhu.lei
 * @date 2023-07-28 15:05
 */
public class IntervalTree<K extends Comparable<K>> {
    public RedBlackNode<K> parent;
    public RedBlackNode<K> left;
    public RedBlackNode<K> right;
    public Color color;
    public K value;


}
