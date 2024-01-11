package org.example.binarytree;

/**
 * 对称二叉树
 */
public class E04Leetcode101 {
    public static void main(String[] args) {
        // 对称
        TreeNode root1 = new TreeNode(
                new TreeNode(new TreeNode(3),2,new TreeNode(4)),
                1,
                new TreeNode(new TreeNode(4),2,new TreeNode(3)));
        System.out.println(isSymmetric(root1));
        // 非对称
        TreeNode root2 = new TreeNode(
                new TreeNode(new TreeNode(3),2,new TreeNode(4)),
                1,
                new TreeNode(new TreeNode(3),2,new TreeNode(4)));
        System.out.println(isSymmetric(root2));

    }
    public static boolean isSymmetric(TreeNode root){
        return check(root.left, root.right);
    }

    private static boolean check(TreeNode left, TreeNode right) {
        if(left == null && right == null) {
            return true;
        }
        // 有了上面的判断，到这里 left 和 right 肯定不能同时为 null
        if(right == null || left == null) {
            return false;
        }
        if(left.val != right.val) {
            return false;
        }
        return check(left.left, right.right) && check(left.right, right.left);
    }

}

