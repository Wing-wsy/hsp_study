package org.example.binarytree;

/**
 * @author wing
 * @create 2024/1/10
 */
public class E05Leetcode104_1 {
    public static void main(String[] args) {
        // 对称
        TreeNode root1 = new TreeNode(
                new TreeNode(new TreeNode(3),2,new TreeNode(4)),
                1,
                new TreeNode(new TreeNode(4),2,new TreeNode(3)));
        System.out.println(maxDepth(root1));

    }
    public static int maxDepth(TreeNode node){
        if(node == null){
            return 0;
        }
        int d1 = maxDepth(node.left);
        int d2 = maxDepth(node.right);
        return Integer.max(d1,d2) + 1;
    }
}
