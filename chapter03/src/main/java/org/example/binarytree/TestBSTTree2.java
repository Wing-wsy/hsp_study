package org.example.binarytree;

/**
 * @author wing
 * @create 2024/1/11
 */
public class TestBSTTree2 {
    public static BSTTree2 createTree() {
        /*
                        4
                       /  \
                      2    6
                    / \    / \
                   1   3  5   7
         */
        BSTTree2.BSTNode<String,String> n1 = new BSTTree2.BSTNode<>("a", "张无忌");
        BSTTree2.BSTNode<String,String> n3 = new BSTTree2.BSTNode<>("c", "宋青书");
        BSTTree2.BSTNode<String,String> n2 = new BSTTree2.BSTNode<>("b", "周芷若", n1, n3);

        BSTTree2.BSTNode<String,String> n5 = new BSTTree2.BSTNode<>("e", "说不得");
        BSTTree2.BSTNode<String,String> n7 = new BSTTree2.BSTNode<>("g", "殷离");
        BSTTree2.BSTNode<String,String> n6 = new BSTTree2.BSTNode<>("f", "赵敏", n5, n7);
        BSTTree2.BSTNode<String,String> root = new BSTTree2.BSTNode<>("d", "小昭", n2, n6);

        BSTTree2 tree = new BSTTree2();
        tree.root = root;
        return tree;
    }
    public static void main(String[] args) {
        BSTTree2<String,String> tree = createTree();
        System.out.println(tree.get("g"));
        System.out.println(tree.min());
        System.out.println(tree.max());
    }
}
