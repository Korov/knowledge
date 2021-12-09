package org.algorithms.example.redblacktree;

import lombok.extern.slf4j.Slf4j;

/**
 * @author korov
 */
@Slf4j
public class RedBlackUtil {
    public static final RedBlackNode NULL_NODE = new RedBlackNode(null, null, null, Color.BLACK, null);

    /**
     * 对node进行左旋
     */
    public static <K extends Comparable<K>> RedBlackNode<K> leftRotate(RedBlackNode<K> root, RedBlackNode<K> node) {
        RedBlackNode<K> rightNode = node.right;
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
    public static <K extends Comparable<K>> RedBlackNode<K> rightRotate(RedBlackNode<K> root, RedBlackNode<K> node) {
        RedBlackNode<K> leftNode = node.left;
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
    public static <K extends Comparable<K>> RedBlackNode<K> rbInsert(RedBlackNode<K> root, RedBlackNode<K> insertNode) {

        RedBlackNode<K> insertIndex = root;
        RedBlackNode<K> parentInsertIndex = insertIndex.parent;
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
    private static <K extends Comparable<K>> RedBlackNode<K> rbInsertFixup(RedBlackNode<K> root, RedBlackNode<K> insertNode) {
        while (insertNode.parent.color == Color.RED) {
            if (insertNode.parent == insertNode.parent.parent.left) {
                RedBlackNode<K> uncleNode = insertNode.parent.parent.right;
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
                RedBlackNode<K> uncleNode = insertNode.parent.parent.left;
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

    public static <K extends Comparable<K>> RedBlackNode<K> rbTransplant(RedBlackNode<K> root, RedBlackNode<K> placedNode, RedBlackNode<K> placeNode) {
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

    public static <K extends Comparable<K>> RedBlackNode<K> rbDeleteFixup(RedBlackNode<K> root, RedBlackNode<K> deleteNode) {
        while (deleteNode != root && deleteNode.color == Color.BLACK) {
            if (deleteNode == deleteNode.parent.left) {
                RedBlackNode<K> brotherNode = deleteNode.parent.right;
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
                RedBlackNode<K> brotherNode = deleteNode.parent.left;
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

    public static <K extends Comparable<K>> RedBlackNode<K> rbDelete(RedBlackNode<K> root, RedBlackNode<K> deleteNode) {
        RedBlackNode<K> childNode;
        RedBlackNode<K> originalNode = deleteNode;
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

    /**
     * 中序遍历
     *
     * @param tree
     */
    public static <K extends Comparable<K>> void inorderTraversal(RedBlackNode<K> tree) {
        if (tree == NULL_NODE) {
            return;
        }
        inorderTraversal(tree.left);
        log.info(String.valueOf(tree.value));
        inorderTraversal(tree.right);
    }

    public static <K extends Comparable<K>> RedBlackNode<K> minimum(RedBlackNode<K> node) {
        while (node.left != NULL_NODE) {
            node = node.left;
        }
        return node;
    }

    public static <K extends Comparable<K>> RedBlackNode<K> maximum(RedBlackNode<K> node) {
        while (node.right != NULL_NODE) {
            node = node.right;
        }
        return node;
    }

    public static <K extends Comparable<K>> RedBlackNode<K> successor(RedBlackNode<K> node) {
        if (node.right != NULL_NODE) {
            return minimum(node.right);
        }

        RedBlackNode<K> parent = node.parent;
        while (parent != NULL_NODE && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    public static <K extends Comparable<K>> RedBlackNode<K> predecessor(RedBlackNode<K> node) {
        if (node.left != NULL_NODE) {
            return maximum(node.left);
        }

        RedBlackNode<K> parent = node.parent;
        while (parent != NULL_NODE && node == parent.left) {
            node = parent;
            parent = parent.parent;
        }

        return parent;
    }

    public static <K extends Comparable<K>> RedBlackNode<K> treeSearch(RedBlackNode<K> node, K key) {
        if (node == NULL_NODE || key.compareTo(node.value) == 0) {
            return node;
        }

        if (key.compareTo(node.value) < 0) {
            return treeSearch(node.left, key);
        }
        return treeSearch(node.right, key);
    }
}
