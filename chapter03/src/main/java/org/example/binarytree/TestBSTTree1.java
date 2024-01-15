package org.example.binarytree;

import org.junit.Test;

/**
 * @author wing
 * @create 2024/1/11
 */
public class TestBSTTree1 {

    //
    public static BSTTree1 createTree() {

        /*
                        4
                       /  \
                      2    6
                    / \    / \
                   1   3  5   7
         */
        BSTTree1.BSTNode n1 = new BSTTree1.BSTNode(1, "张无忌");
        BSTTree1.BSTNode n3 = new BSTTree1.BSTNode(3, "宋青书");
        BSTTree1.BSTNode n2 = new BSTTree1.BSTNode(2, "周芷若", n1, n3);

        BSTTree1.BSTNode n5 = new BSTTree1.BSTNode(5, "说不得");
        BSTTree1.BSTNode n7 = new BSTTree1.BSTNode(7, "殷离");
        BSTTree1.BSTNode n6 = new BSTTree1.BSTNode(6, "赵敏", n5, n7);
        BSTTree1.BSTNode root = new BSTTree1.BSTNode(4, "小昭", n2, n6);

        BSTTree1 tree = new BSTTree1();
        tree.root = root;
        return tree;
    }

    public static void main(String[] args) {
        BSTTree1 tree = createTree();
        System.out.println(tree.get(7));
    }

}
