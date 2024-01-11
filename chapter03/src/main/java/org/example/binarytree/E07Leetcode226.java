package org.example.binarytree;

/**
 * @author wing
 * @create 2024/1/10
 */
public class E07Leetcode226 {
    public static void main(String[] args) {
        // 对称
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(4),2,new TreeNode(new TreeNode(7),5,null)),
                1,
                new TreeNode(null,3,new TreeNode(6)));
        fn(root);
        System.out.println(root);

    }

    public static void fn(TreeNode node){
        if(node == null)
            return;
        TreeNode t = node.left;
        node.left = node.right;
        node.right = t;
        fn(node.left);
        fn(node.right);
    }
}
