package org.algorithms.example.intervaltree;

import org.algorithms.example.redblacktree.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhu.lei
 * @date 2023-07-28 15:30
 */
public class IntervalTreeUtil {
    public static final IntervalTreeNode NULL_NODE = new IntervalTreeNode(null, null, null, Color.BLACK, null);

    /**
     * 对node进行左旋
     */
    private static <T extends Comparable<T>, V> IntervalTreeNode<T, V> leftRotate(IntervalTreeNode<T, V> root, IntervalTreeNode<T, V> node) {
        IntervalTreeNode<T, V> rightNode = node.right;
        node.right = rightNode.left;
        if (rightNode.left != NULL_NODE) {
            rightNode.left.parent = node;
        }

        rightNode.parent = node.parent;
        if (node.parent == NULL_NODE) {
            root = rightNode;
        } else if (node == node.parent.left) {
            node.parent.left = rightNode;
        } else {
            node.parent.right = rightNode;
        }
        rightNode.left = node;
        node.parent = rightNode;
        return root;
    }

    /**
     * 对node进行右旋
     */
    private static <T extends Comparable<T>, V> IntervalTreeNode<T, V> rightRotate(IntervalTreeNode<T, V> root, IntervalTreeNode<T, V> node) {
        IntervalTreeNode<T, V> leftNode = node.left;
        node.left = leftNode.right;
        if (leftNode.right != NULL_NODE) {
            leftNode.right.parent = node;
        }

        leftNode.parent = node.parent;
        if (node.parent == NULL_NODE) {
            root = leftNode;
        } else if (node == node.parent.left) {
            node.parent.left = leftNode;
        } else {
            node.parent.right = leftNode;
        }
        leftNode.right = node;
        node.parent = leftNode;
        return root;
    }

    /**
     * 找到合适的位置将节点插入树中
     */
    public static <T extends Comparable<T>, V> IntervalTreeNode<T, V> rbInsert(IntervalTreeNode<T, V> root, IntervalTreeNode<T, V> insertNode) {

        IntervalTreeNode<T, V> insertIndex = root;
        IntervalTreeNode<T, V> parentInsertIndex = insertIndex.parent;
        // 找到插入点
        while (insertIndex != NULL_NODE) {
            parentInsertIndex = insertIndex;
            if (insertNode.value.compareTo(insertIndex.value) > 0) {
                insertIndex = insertIndex.right;
            } else {
                insertIndex = insertIndex.left;
            }
        }
        insertNode.parent = parentInsertIndex;
        if (parentInsertIndex == NULL_NODE) {
            root = insertIndex;
        } else if (insertNode.value.compareTo(parentInsertIndex.value) > 0) {
            parentInsertIndex.right = insertNode;
        } else {
            parentInsertIndex.left = insertNode;
        }
        insertNode.color = Color.RED;
        insertNode.left = NULL_NODE;
        insertNode.right = NULL_NODE;
        return rbInsertFixup(root, insertNode);
    }

    /**
     * 保持树的红黑性
     */
    private static <T extends Comparable<T>, V> IntervalTreeNode<T, V> rbInsertFixup(IntervalTreeNode<T, V> root, IntervalTreeNode<T, V> insertNode) {
        while (insertNode.parent.color == Color.RED) {
            if (insertNode.parent == insertNode.parent.parent.left) {
                IntervalTreeNode<T, V> uncleNode = insertNode.parent.parent.right;
                if (uncleNode.color == Color.RED) {
                    insertNode.parent.color = Color.BLACK;
                    uncleNode.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    insertNode = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.right) {
                    insertNode = insertNode.parent;
                    root = leftRotate(root, insertNode);
                } else {
                    insertNode.parent.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    root = rightRotate(root, insertNode.parent.parent);
                }
            } else {
                IntervalTreeNode<T, V> uncleNode = insertNode.parent.parent.left;
                if (uncleNode.color == Color.RED) {
                    insertNode.parent.color = Color.BLACK;
                    uncleNode.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    insertNode = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.left) {
                    insertNode = insertNode.parent;
                    root = rightRotate(root, insertNode);
                } else {
                    insertNode.parent.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    root = leftRotate(root, insertNode.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
        return root;
    }

    public static <T extends Comparable<T>, V> IntervalTreeNode<T, V> rbDelete(IntervalTreeNode<T, V> root, IntervalTreeNode<T, V> deleteNode) {
        IntervalTreeNode<T, V> childNode;
        IntervalTreeNode<T, V> originalNode = deleteNode;
        Color originalColor = originalNode.color;
        if (deleteNode.left == NULL_NODE) {
            childNode = deleteNode.right;
            root = rbTransplant(root, deleteNode, deleteNode.right);
        } else if (deleteNode.right == NULL_NODE) {
            childNode = deleteNode.left;
            root = rbTransplant(root, deleteNode, deleteNode.left);
        } else {
            originalNode = minimum(deleteNode.right);
            originalColor = originalNode.color;
            childNode = originalNode.right;
            if (originalNode.parent == deleteNode) {
                childNode.parent = originalNode;
            } else {
                root = rbTransplant(root, originalNode, originalNode.right);
                originalNode.right = deleteNode.right;
                originalNode.right.parent = originalNode;
            }
            root = rbTransplant(root, deleteNode, originalNode);
            originalNode.left = deleteNode.left;
            originalNode.left.parent = originalNode;
            originalNode.color = deleteNode.color;
        }
        if (originalColor == Color.BLACK) {
            root = rbDeleteFixup(root, childNode);
        }
        return root;
    }

    private static <T extends Comparable<T>, V> IntervalTreeNode<T, V> rbTransplant(IntervalTreeNode<T, V> root, IntervalTreeNode<T, V> placedNode, IntervalTreeNode<T, V> placeNode) {
        if (placedNode.parent == NULL_NODE) {
            root = placeNode;
        } else if (placedNode == placedNode.parent.left) {
            placedNode.parent.left = placeNode;
        } else {
            placedNode.parent.right = placeNode;
        }
        placeNode.parent = placedNode.parent;
        return root;
    }

    private static <T extends Comparable<T>, V> IntervalTreeNode<T, V> rbDeleteFixup(IntervalTreeNode<T, V> root, IntervalTreeNode<T, V> deleteNode) {
        while (deleteNode != root && deleteNode.color == Color.BLACK) {
            if (deleteNode == deleteNode.parent.left) {
                IntervalTreeNode<T, V> brotherNode = deleteNode.parent.right;
                if (brotherNode.color == Color.RED) {
                    brotherNode.color = Color.BLACK;
                    deleteNode.parent.color = Color.RED;
                    root = leftRotate(root, deleteNode.parent);
                    brotherNode = deleteNode.parent.right;
                }
                if (brotherNode.left.color == Color.BLACK && brotherNode.right.color == Color.BLACK) {
                    brotherNode.color = Color.RED;
                    deleteNode = deleteNode.parent;
                } else {
                    if (brotherNode.right.color == Color.BLACK) {
                        brotherNode.left.color = Color.BLACK;
                        brotherNode.color = Color.RED;
                        root = rightRotate(root, brotherNode);
                        brotherNode = deleteNode.parent.right;
                    }
                    brotherNode.color = deleteNode.parent.color;
                    deleteNode.parent.color = Color.BLACK;
                    brotherNode.right.color = Color.BLACK;
                    root = leftRotate(root, deleteNode.parent);
                    deleteNode = root;
                }
            } else {
                IntervalTreeNode<T, V> brotherNode = deleteNode.parent.left;
                if (brotherNode.color == Color.RED) {
                    brotherNode.color = Color.BLACK;
                    deleteNode.parent.color = Color.RED;
                    root = rightRotate(root, deleteNode.parent);
                    brotherNode = deleteNode.parent.left;
                }

                if (brotherNode.left.color == Color.BLACK && brotherNode.right.color == Color.BLACK) {
                    brotherNode.color = Color.RED;
                    deleteNode = deleteNode.parent;
                } else {
                    if (brotherNode.left.color == Color.BLACK) {
                        brotherNode.right.color = Color.BLACK;
                        brotherNode.color = Color.RED;
                        root = leftRotate(root, brotherNode);
                        brotherNode = deleteNode.parent.left;
                    }
                    brotherNode.color = deleteNode.parent.color;
                    deleteNode.parent.color = Color.BLACK;
                    brotherNode.left.color = Color.BLACK;
                    root = rightRotate(root, deleteNode.parent);
                    deleteNode = root;
                }
            }
        }
        deleteNode.color = Color.BLACK;
        return root;
    }

    private static <T extends Comparable<T>, V> IntervalTreeNode<T, V> minimum(IntervalTreeNode<T, V> node) {
        while (node.left != NULL_NODE) {
            node = node.left;
        }
        return node;
    }

    public static <T extends Comparable<T>, V> IntervalTreeNode<T, V> treeSearch(IntervalTreeNode<T, V> node, IntervalValue<T, V> key) {
        if (node == NULL_NODE) {
            return node;
        }
        if (node.value.getLow().compareTo(key.getLow()) >= 0 && node.value.getHigh().compareTo(key.getHigh()) <= 0) {
            return node;
        }
        resetMax(node);

        if (node.left.calcMax().compareTo(key.getLow()) >= 0) {
            return treeSearch(node.left, key);
        }
        return treeSearch(node.right, key);
    }

    public static <T extends Comparable<T>, V> List<IntervalTreeNode<T, V>> listTreeSearch(IntervalTreeNode<T, V> node, IntervalValue<T, V> key) {
        List<IntervalTreeNode<T, V>> result = new ArrayList<>();
        if (node == NULL_NODE) {
            return result;
        }
        if (node.value.getLow().compareTo(key.getLow()) <= 0 && node.value.getHigh().compareTo(key.getHigh()) >= 0) {
            result.add(node);
        }
        resetMax(node);
        if (node.left != NULL_NODE && node.left.calcMax().compareTo(key.getHigh()) >= 0) {
            result.addAll(listTreeSearch(node.left, key));
        }
        if (node.right != NULL_NODE && node.value.getLow().compareTo(key.getLow()) <= 0) {
            result.addAll(listTreeSearch(node.right, key));
        }
        return result;
    }


    public static <T extends Comparable<T>, V> void resetMax(IntervalTreeNode<T, V> tree) {
        if (tree == NULL_NODE) {
            return;
        }
        tree.value.setMax(null);
        resetMax(tree.left);
        resetMax(tree.right);
    }
}
