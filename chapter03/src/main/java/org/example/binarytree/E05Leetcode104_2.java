package org.example.binarytree;

import java.util.LinkedList;

/**
 * @author wing
 * @create 2024/1/10
 */
public class E05Leetcode104_2 {
    public static void main(String[] args) {
        // 对称
        TreeNode root1 = new TreeNode(
                new TreeNode(new TreeNode(3),2,new TreeNode(4)),
                1,
                new TreeNode(new TreeNode(4),2,new TreeNode(3)));
        System.out.println(maxDepth(root1));

    }

    /*
       思路：
       1. 使用非递归后序遍历，栈的最大高度即为最大深度
    * */
    public static int maxDepth(TreeNode root){
        TreeNode curr = root;
        TreeNode pop = null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        int max = 0;  // 栈的最大高度
        while(curr != null || !stack.isEmpty()){
            if(curr != null){
                stack.push(curr);
                int size = stack.size();
                if(size > max){
                    max = size;
                }
                curr = curr.left;
            }else{
                TreeNode peek = stack.peek();
                if(peek.right == null || peek.right == pop){
                    pop = stack.pop();
                }else{
                    curr = peek.right;
                }
            }
        }
        return max;
    }
}
