package org.example.binarytree;

/**
 * 二叉搜索树
 */
public class BSTTree1 {
    BSTNode root;

    /**
     * 查找关键字对应的值(递归方式)
     * */
//    public Object get(int key) {
//        return doGet(root, key);
//    }
//    private Object doGet(BSTNode node, int key) {
//        if (node == null) {
//            return null;  //没找到
//        }
//        if (key < node.key) {
//            return doGet(node.left, key);
//        } else if (node.key < key) {
//            return doGet(node.right, key);
//        } else {
//            return node.value;  //找到了
//        }
//    }

    /**
     * 查找关键字对应的值(循环方式)
     * */
    public Object get(int key) {
        BSTNode node = root;
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            }else if (node.key < key) {
                node = node.right;
            }else {
                return node.value;
            }
        }
        return null;
    }


    /**
     * 查找最小关键字对应的值
     * */
    public Object min() {
        return null;
    }
    /**
     * 查找最大关键字对应的值
     * */
    public Object max() {
        return null;
    }
    /**
     * 存储关键字和对应的值
     * */
    public void put(int key, Object value) {

    }
    /**
     * 查找关键字的前驱值
     * */
    public Object successor(int key) {
        return null;
    }
    /**
     * 查找关键字的后继值
     * */
    public Object predecessor(int key) {
        return null;
    }

    /* 节点类 */
    static class BSTNode {
        int key;
        Object value;
        BSTNode left;
        BSTNode right;
        public BSTNode(int key) {
            this.key = key;
        }
        public BSTNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }
        public BSTNode(int key, Object value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
