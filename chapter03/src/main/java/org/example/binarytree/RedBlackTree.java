package org.example.binarytree;

import static org.example.binarytree.RedBlackTree.Color.BLACK;
import static org.example.binarytree.RedBlackTree.Color.RED;

/**
 * @author wing
 * @create 2024/1/14
 * 红黑树
 */
public class RedBlackTree {

    enum Color {
        RED, BLACK;
    }

    private Node root;

    private static class Node {
        int key;
        Object value;
        Node left;
        Node right;
        Node parent;    // 父节点，相比平衡二叉树增加的属性
        Color color = RED;    // 新增节点默认都是红色

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        // 是否是左孩子
        boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        // 叔叔
        Node uncle() {
            if (parent == null || parent.parent == null) {
                return null;
            }
            if (parent.isLeftChild()) {
                return parent.parent.right;
            } else {
                return parent.parent.left;
            }
        }

        // 兄弟
        Node sibling() {
            if (parent == null) {
                return null;
            }
            if (this.isLeftChild()) {
                return parent.right;
            } else {
                return parent.left;
            }
        }
    }

    // 判断红
    boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    // 判断黑
    boolean isBlack(Node node) {
        // return !isRed(node);
        return node != null && node.color == BLACK;
    }

    // 右旋（仅仅是旋转，维护好树的父子关系，但是不维护节点的颜色）
    private void rightRotate(Node pink) {
        Node parent = pink.parent;
        Node yellow = pink.left;
        Node green = yellow.right;
        if (green != null) {
            green.parent = pink;
        }
        yellow.right = pink;
        yellow.parent = parent;
        pink.left = green;
        pink.parent = yellow;
        if (parent == null) {
            root = yellow;
        } else if (parent.left == pink) {
            parent.left = yellow;
        } else {
            parent.right = yellow;
        }
    }
    // 左旋
    private void leftRotate(Node pink) {
        // 参考右旋
    }

    // 新增或更新（正常增，遇到红红不平衡进行调整）
    public void put(int key, Object value) {
        Node p = root;
        Node parent = null;
        while (p != null) {
            parent = p;
            if (key < p.key) {
                p = p.left;
            } else if (key > p.key) {
                p = p.right;
            } else {
                // 找到，更新
                p.value = value;
                return;
            }
        }
        Node inserted = new Node(key, value);
        if (parent == null) {
            root = inserted;
        } else if (parent.key > key) {
            parent.left = inserted;
            inserted.parent = parent;
        } else {
            parent.right = inserted;
            inserted.parent = parent;
        }
        // 插入遇到红红则进行调整
        fixRedRed(inserted);
    }

    // 红红则进行调整
    void fixRedRed(Node x) {
        // Case1: 插入节点为根节点，将根节点变黑
        if (x == root) {
            x.color = BLACK;
            return;
        }
        // Case2: 插入节点的父亲若为黑色，树的红黑性质不变，无需调整
        if (isBlack(x.parent)) {
            return;
        }
        // Case3 和 Case4 会触发红红相邻
        // Case3:  叔叔为红色（操作步骤如下）【变色就能解决平衡】
        //    1）父亲变为黑色，为了保证黑色平衡，连带的叔叔也变为黑色
        //    2）祖父如果是黑色不变的话会造成这颗子树黑色过多，因此祖父节点变为红色
        //    3）祖父变为红色的话，可能会接着触发红红相邻，因此对祖父进行递归调整
        Node parent = x.parent;
        Node uncle = x.uncle();
        Node grandParent = parent.parent;
        if (isRed(uncle)) {
            parent.color = BLACK;
            uncle.color = BLACK;
            grandParent.color = RED;
            // 递归调用
            fixRedRed(grandParent);
            return;
        }
        // Case4:  叔叔为黑色（分下面四种情况）【当有父亲，没有叔叔时，null为黑色，也属于叔叔为黑色的情况】【变色后还不能解决平衡，还需要配合旋转】
        if (parent.isLeftChild() && x.isLeftChild()) {
            // 1）父亲为左孩子，插入节点也是左孩子，此时即 LL 不平衡【操作：父亲变黑，祖父变红，右旋】
            parent.color = BLACK;
            grandParent.color = RED;
            rightRotate(grandParent);
        } else if (parent.isLeftChild()) {
            // 2）父亲为左孩子，插入节点是右孩子，此时即 LR 不平衡
            leftRotate(parent);
            x.color = BLACK;
            grandParent.color = RED;
            rightRotate(grandParent);
        } else if (!x.isLeftChild()) {
            // 3）父亲为右孩子，插入节点也是右孩子，此时即 RR 不平衡
            parent.color = BLACK;
            grandParent.color = RED;
            leftRotate(grandParent);
        } else {
            // 4）父亲为右孩子，插入节点是左孩子，此时即 RL 不平衡
            rightRotate(parent);
            x.color = BLACK;
            grandParent.color = RED;
            leftRotate(grandParent);
        }
    }

    // 删除（正常删，会用到李代桃僵技巧，遇到黑黑不平衡进行调整）
    public void remove(int key) {
        Node deleted = find(key);
        if (deleted == null) {
            return;
        }
        doRemove(deleted);
    }

    private void fixDouleBlack(Node x) {
        if (x == root) {
            return;
        }
        Node parent = x.parent;
        Node sibling = x.sibling();
        if (isRed(sibling)) {
            if (x.isLeftChild()) {
                leftRotate(parent);
            } else {
                rightRotate(parent);
            }
            parent.color = RED;
            sibling.color = BLACK;
            fixDouleBlack(x);
            return;
        }

        if (sibling != null) {
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                sibling.color = RED;
                if (isRed(parent)) {
                    parent.color = BLACK;
                } else {
                    fixDouleBlack(parent);
                }
            } else {
                // LL
                if (sibling.isLeftChild() && isRed(sibling.left)) {
                    rightRotate(parent);
                    sibling.left.color = BLACK;
                    sibling.color = parent.color;
                }
                // LR
                else if (sibling.isLeftChild() && isRed(sibling.right)) {
                    sibling.right.color = parent.color;
                    leftRotate(sibling);
                    rightRotate(parent);
                }
                // RL
                else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                    sibling.left.color = parent.color;
                    rightRotate(sibling);
                    leftRotate(parent);
                }
                // RR
                else {
                    leftRotate(parent);
                    sibling.right.color = parent.color;
                    sibling.color = parent.color;
                }
                parent.color = BLACK;
            }
        } else {
            // 留给学生思考，看会不会走到这里
            fixDouleBlack(parent);
        }
    }
    
    private void doRemove(Node deleted) {
        Node replaced = findReplaced(deleted);
        Node parent = deleted.parent;
        // 没有孩子
        if (replaced == null) {
            // case 1 删除的是根节点
            if (deleted == root) {
                root = null;
            } else {
                if (isBlack(deleted)) {
                   // 复杂调整
                    fixDouleBlack(deleted);
                } else {
                    // 红色叶子，无需任何处理
                }
                if (deleted.isLeftChild()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                deleted.parent = null;  // help gc
            }
            return;
        }
        // 有一个孩子
        if (deleted.left == null || deleted.right == null) {
            // case 1 删除的是根节点
            if (deleted == root) {
                root.key = replaced.key;
                root.value = replaced.value;
                root.left = root.right = null;
            } else {
                if (deleted.isLeftChild()) {
                    parent.left = replaced;
                } else {
                    parent.right = replaced;
                }
                replaced.parent = parent;
                deleted.left = deleted.right = deleted.parent = null; // help gc
                if (isBlack(deleted) && isBlack(replaced)) {
                    // 复杂处理
                    fixDouleBlack(replaced);
                } else {
                    replaced.color = BLACK;
                }
            }
            return;
        }

        // case 0：有两个孩子(李代桃僵技巧 -> 将有两个孩子转成有一个孩子或者没有孩子的节点来删除，复杂问题简单化)
        int t = deleted.key;
        deleted.key = replaced.key;
        replaced.key = t;

        Object v = deleted.value;
        deleted.value = replaced.value;
        replaced.value = v;
        doRemove(replaced);
    }

    // 查找删除节点
    Node find(int key) {
        Node p = root;
        while (p != null) {
            if (key < p.key) {
                p = p.left;
            } else if (key > p.key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    // 查找后继节点
    Node findReplaced(Node deleted) {
        if (deleted.left == null && deleted.right == null) {
           return null;
        }
        if (deleted.left == null) {
            return deleted.right;
        }
        if (deleted.right == null) {
            return deleted.left;
        }
        Node s = deleted.right;
        while (s.left != null) {
            s = s.left;
        }
        return s;
    }
}
