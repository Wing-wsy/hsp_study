package org.example.binarytree;

import java.util.Arrays;

/**
 * 根据前序后序遍历结果构造二叉树
 */
public class E10Leetcode106 {
    /*
        中序：inOrder = {4,2,1,6,3,7}
        后序：postOrder = {4,2,6,7,3,1}

                 1
                / \
               2   3
              /   / \
             4   6   7
     */
    public static void main(String[] args) {
        int[] preOrder = {1,2,4,3,6,7};
        int[] inOrder = {4,2,1,6,3,7};
        TreeNode treeNode = buildTree(preOrder, inOrder);
        System.out.println(treeNode);

    }

    public static TreeNode buildTree(int[] inOrder, int[] postOrder){
        if (inOrder.length == 0)
            return null;

        // 创建根节点
        int rootValue = postOrder[postOrder.length - 1];
        TreeNode root = new TreeNode(rootValue);
        // 区分左右子树
        for (int i = 0; i < inOrder.length; i++) {
            if (inOrder[i] == rootValue) {
                int[] inLeft = Arrays.copyOfRange(inOrder, 0, i);
                int[] inRight = Arrays.copyOfRange(inOrder, i + 1, inOrder.length);

                int[] postLeft = Arrays.copyOfRange(postOrder, 0, i);
                int[] postRight = Arrays.copyOfRange(postOrder, i, postOrder.length - 1);
                root.left = buildTree(inLeft, postLeft);
                root.right = buildTree(inRight, postRight);
                break;
            }
        }
        return root;
    }

}

