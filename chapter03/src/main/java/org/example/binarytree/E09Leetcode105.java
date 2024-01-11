package org.example.binarytree;

import java.util.Arrays;

/**
 * 根据前序中序遍历结果构造二叉树
 */
public class E09Leetcode105 {
    /*
        前序：preOrder = {1,2,4,3,6,7}
        中序：inOrder = {4,2,1,6,3,7}

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

    public static TreeNode buildTree(int[] preOrder, int[] inOrder){
        if (preOrder.length == 0)
            return null;

        // 创建根节点
        int rootValue = preOrder[0];
        TreeNode root = new TreeNode(rootValue);
        // 区分左右子树
        for (int i = 0; i < inOrder.length; i++) {
            if (inOrder[i] == rootValue) {
                int[] inLeft = Arrays.copyOfRange(inOrder, 0, i);  //[4,2]
                int[] inRight = Arrays.copyOfRange(inOrder, i + 1, inOrder.length);  //[6,3,7]

                int[] preLeft = Arrays.copyOfRange(preOrder, 1, i + 1);  //[2,4]
                int[] preRight = Arrays.copyOfRange(preOrder, i + 1, inOrder.length);  //[3,6,7]
                root.left = buildTree(preLeft, inLeft);
                root.right = buildTree(preRight, inRight);
                break;
            }
        }
        return root;
    }

}

