package org.example.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉搜索树
 */
public class BSTTree2<K extends Comparable<K>,V> {
    BSTNode<K,V> root;
    /**
     * 查找关键字对应的值(循环方式)
     * */
    public V get(K key) {
        // 这里可以优化 key 可能null
        BSTNode<K,V> p = root;
        while (p != null) {
            int result = key.compareTo(p.key);
            if (result < 0) {
                p = p.left;
            } else if (result > 0) {
                p = p.right;
            } else {
                return p.value;
            }
        }
        return null;
    }
    /**
     * 查找最小关键字对应的值(递归版)
     * */
//    public V min() {
//        return doMin(root);
//    }
//    private V doMin(BSTNode<K,V> node) {
//        if (node == null) {
//            return null;
//        }
//        if (node.left == null) {
//            return node.value;
//        }
//        return doMin(node.left);
//    }

    /**
     * 查找最小关键字对应的值(非递归版)
     * */
    public V min() {
        return min(root);
     }
    /**
     * 查找指定节点子树的最小值
     * */
    public V min(BSTNode node) {
        if (node == null) {
            return null;
        }
        BSTNode<K,V> p = node;
        while (p.left != null) {
            p = p.left;
        }
        return p.value;
    }
    /**
     * 查找最大关键字对应的值
     * */
    public V max() {
        return max(root);
    }
    /**
     * 查找指定节点子树的最大值
     * */
    public V max(BSTNode node) {
        if (node == null) {
            return null;
        }
        BSTNode<K,V> p = node;
        while (p.right != null) {
            p = p.right;
        }
        return p.value;
    }
    /**
     * 存储关键字和对应的值
     * */
    public void put(K key, V value) {
        BSTNode<K,V> p = root;
        BSTNode<K,V> parent = null;
        while (p != null) {
            parent = p;
            int result = key.compareTo(p.key);
            if (result < 0) {
                p = p.left;
            } else if (result > 0) {
                p = p.right;
            } else {
                // 1.key 存在 更新
                p.value = value;
                return;
            }
        }
        // 2.key 不存在 新增
        if(parent == null){
            root = new BSTNode<>(key,value);
            return;
        }
        int result = key.compareTo(parent.key);
        if (result < 0) {
            parent.left = new BSTNode<>(key,value);
        } else {
            parent.right = new BSTNode<>(key,value);
        }
        new BSTNode(key,value);
    }
    /**
     * 查找关键字的前任值
     * */
    public Object predecessor(K key) {
        BSTNode<K,V> p = root;
        BSTNode<K,V> ancestorFromLeft = null;
        while (p != null) {
            int result = key.compareTo(p.key);
            if (result < 0) {
                p = p.left;
            } else if (result > 0) {
                ancestorFromLeft = p;
                p = p.right;
            } else {
                break;
            }
        }
        // 没找到节点
        if (p == null) {
            return null;
        }
        // 找到节点
        // 情况1：节点有左子树，此时前任就是左子树的最大值
        if (p.left != null) {
            return max(p.left);
        }
        // 情况2：节点没有左子树，则离它最近，自左而来的祖先就是前任
        return ancestorFromLeft != null ? ancestorFromLeft.value : null;
    }

    /**
     * 查找关键字的后任值
     * */
    public Object successor(K key) {
        BSTNode<K,V> p = root;
        BSTNode<K,V> ancestorFromRight = null;
        while (p != null) {
            int result = key.compareTo(p.key);
            if (result < 0) {
                ancestorFromRight = p;
                p = p.left;
            } else if (result > 0) {
                p = p.right;
            } else {
                break;
            }
        }
        if (p == null) {
            return null;
        }
        if (p.right != null) {
            return min(p.right);
        }
        return ancestorFromRight != null ? ancestorFromRight.value : null;
    }

    /**
     * 删除关键字
     * */
    public Object delete(K key) {
        BSTNode<K,V> p = root;
        BSTNode<K,V> parent = null;
        while (p != null) {
            int result = key.compareTo(p.key);
            if (result < 0) {
                parent = p;
                p = p.left;
            } else if (result > 0) {
                parent = p;
                p = p.right;
            } else {
                break;
            }
        }
        if (p == null) {
            return null;
        }
        // 删除操作
        if (p.left == null) {
            // 情况1 或 情况3
            shift(parent, p, p.right);
        } else if (p.right == null) {
            // 情况2 或 情况3
            shift(parent, p, p.left);
        } else {
            // 情况4
            // 4.1 被删除节点找后继
            BSTNode s = p.right;
            BSTNode sParent = p;   // 后继父亲
            while (s.left != null) {
                sParent = s;
                s = s.left;
            }
            // 后继节点为 S
            if (sParent != p) {  // 不相邻
                // 4.2 删除和后继不相邻，处理后继的后事
                shift(sParent, s, s.right);  // 执行到这不可能有左孩子
                s.right = p.right;
            }
            // 4.3 后继取代被删除节点
            shift(parent, p, s);
            s.left = p.left;
        }
        return p.value;
    }

//    public V delete(K key) {
//        ArrayList<V> result = new ArrayList<>();
//        root = doDelete(root, key, result);
//        return result.isEmpty() ? null : result.get(0);
//    }

    /**
     * 递归删除版
     */
//    private BSTNode doDelete(BSTNode<K,V> node, K key, ArrayList<V> list) {
//        if (node == null) {
//            return null;
//        }
//        int result = key.compareTo(node.key);
//        if (result < 0) {
//            node.left = doDelete(node.left, key, list);
//            return node;
//        }
//        if (result > 0) {
//            node.right = doDelete(node.right, key, list);
//            return node;
//        }
//        list.add(node.value);
//
//        // 找到删除的节点
//        // 情况1 - 只有右孩子
//        if (node.left == null) {
//            return node.right;
//        }
//        // 情况2 - 只有左孩子
//        if (node.right == null) {
//            return node.left;
//        }
//        // 情况3 - 有两个孩子
//        BSTNode<K,V> s = node.right;
//        while (s.left != null) {
//            s = s.left;
//        }
//        s.right = doDelete(node.right, s.key, new ArrayList<>());
//        s.left = node.left;
//        return s;
//
//    }

    // 找 < key 的所有 value
    public List<V> less(K key) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<K,V> p = root;
        LinkedList<BSTNode> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                BSTNode<K,V> pop = stack.pop();
                int t = key.compareTo(p.key);
                if (t > 0) {
                    result.add(pop.value);
                } else {
                   break;
                }
                p = pop.right;
            }
        }
        return result;
    }

    // 找 > key 的所有 value
//    public List<V> greater(K key) {
//        ArrayList<V> result = new ArrayList<>();
//        BSTNode<K,V> p = root;
//        LinkedList<BSTNode> stack = new LinkedList<>();
//        while (p != null || !stack.isEmpty()) {
//            if (p != null) {
//                stack.push(p);
//                p = p.left;
//            } else {
//                BSTNode<K,V> pop = stack.pop();
//                int t = key.compareTo(p.key);
//                if (t < 0) {
//                    result.add(pop.value);
//                }
//                p = pop.right;
//            }
//        }
//        return result;
//    }

    // 找 > key 的所有 value
    public List<V> greater(K key) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<K,V> p = root;
        LinkedList<BSTNode> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.right;
            } else {
                BSTNode<K,V> pop = stack.pop();
                int t = key.compareTo(p.key);
                if (t < 0) {
                    result.add(pop.value);
                }  else {
                    break;
                }
                p = pop.left;
            }
        }
        return result;
    }

    // 找 >= key1 且 <= key2 的所有 value
    public List<V> between(K key1, K key2) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<K,V> p = root;
        LinkedList<BSTNode> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                BSTNode<K,V> pop = stack.pop();
                int t1 = key1.compareTo(p.key);
                int t2 = key2.compareTo(p.key);
                if (t1 <= 0 && t2 >= 0) {
                    result.add(pop.value);
                } else if (t2 < 0) {
                    break;
                }
                p = pop.right;
            }
        }
        return result;
    }


    /**
     * 托孤方法
     * parent - 被删除节点的父亲
     * deleted - 被删除节点
     * child - 被顶上去的节点
     */
    private void shift(BSTNode parent, BSTNode deleted, BSTNode child) {
        if (parent == null) {
            root = child;
        }else if (deleted == parent.left) {
            parent.left = child;
        }else {
            parent.right = child;
        }
    }

    /* 节点类 */
    static class BSTNode<K,V> {
        K key;
        V value;
        BSTNode<K,V> left;
        BSTNode<K,V> right;
        public BSTNode(K key) {
            this.key = key;
        }
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public BSTNode(K key, V value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
