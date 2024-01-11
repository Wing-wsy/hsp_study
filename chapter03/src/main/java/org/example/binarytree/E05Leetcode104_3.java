package org.example.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author wing
 * @create 2024/1/10
 */
public class E05Leetcode104_3 {
    public static void main(String[] args) {
        // 对称
        TreeNode root1 = new TreeNode(
                new TreeNode(new TreeNode(4),2,new TreeNode(new TreeNode(7),5,null)),
                1,
                new TreeNode(null,3,new TreeNode(6)));
        System.out.println(maxDepth(root1));

    }

    /*
       思路：
       1. 使用非递归后序遍历，栈的最大高度即为最大深度
    * */
    public static int maxDepth(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode poll = queue.poll();
            System.out.println(poll.val);
            if(poll.left != null){
                queue.offer(poll.left);
            }
            if(poll.right != null){
                queue.offer(poll.right);
            }
        }
        return -1;
    }
}
