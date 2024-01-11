package org.example.binarytree;

import java.util.LinkedList;

/**
 * @author wing
 * @create 2024/1/9
 */
public class TreeTraversal {
    public static void main(String[] args) {

        /*
                   1
                  / \
                 2   3
                /   / \
               4   5   6

         */
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(4),2,null),
                1,
                new TreeNode(new TreeNode(5),3,new TreeNode(6)));

        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;
        TreeNode pop = null;
        while(curr != null || !stack.isEmpty()) {
            if(curr != null) {
                stack.push(curr);
                // 待处理左子树
                colorPrintln("前：" + curr.val, 31);  // 31 红色
                curr = curr.left;
            }else {
                TreeNode peek = stack.peek();
                // 没有右子树
                if(peek.right == null) {
                    colorPrintln("中：" + peek.val, 36);  // 36 绿色
                    pop = stack.pop();
                    colorPrintln("后：" + pop.val, 34);  // 34 蓝色
                }
                // 右子树处理完成
                else if(peek.right == pop) {
                    pop = stack.pop();
                    colorPrintln("后：" + pop.val, 34);
                }
                // 待处理右子树
                else {
                    colorPrintln("中：" + peek.val, 36);
                    curr = peek.right;
                }
            }
        }


        /*
        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);*/
    }

    private static void colorPrintln(String origin, int color){
        System.out.printf("\033[%dm%s\033[0m%n", color, origin);
    }

    /**
     *  前序遍历
     * */
    static void preOrder(TreeNode node){
        if(node == null){
            return;
        }
        System.out.print(node.val + "\t");
        preOrder(node.left);
        preOrder(node.right);
    }
    /**
     *  中序遍历
     * */
    static void inOrder(TreeNode node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.print(node.val + "\t");
        inOrder(node.right);
    }
    /**
     *  后序遍历
     * */
    static void postOrder(TreeNode node){
        if(node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + "\t");
    }
}


