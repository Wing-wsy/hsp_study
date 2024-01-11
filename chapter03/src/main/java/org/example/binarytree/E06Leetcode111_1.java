//package org.example.binarytree;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
///**
// * @author wing
// * @create 2024/1/10
// */
//public class E06Leetcode111_1 {
//    public static void main(String[] args) {
//        // 对称
//        TreeNode root1 = new TreeNode(
//                new TreeNode(new TreeNode(4),2,new TreeNode(new TreeNode(7),5,null)),
//                1,
//                new TreeNode(null,3,new TreeNode(6)));
//        System.out.println(m(root1));
//
//    }
//
//    /*
//       思路：
//       1. 使用非递归后序遍历，栈的最大高度即为最大深度
//    * */
//    public static int minDepth(TreeNode node){
//        if(node == null){
//            return 0;
//        }
//        int d1 = minDepth(node.left);
//        int d2 = minDepth(node.right);
//        return Integer.min(d1,d2) + 1;
//    }
//}
