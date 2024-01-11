package org.example.array_;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

/**
 * @author wing
 * @create 2024/1/4
 */
public class DynamicArray {

    static LinkedList<Integer> a = new LinkedList<>();
    static LinkedList<Integer> b = new LinkedList<>();
    static LinkedList<Integer> c = new LinkedList<>();

    static void init(int n){
        for (int i = n; i >= 1 ; i--) {
            a.addLast(i);
        }
    }

    public static void main(String[] args) {
        init(3);
        print();
        move(3, a, b, c);
    }

    private static void move(int n, LinkedList<Integer> a,
                             LinkedList<Integer> b,
                             LinkedList<Integer> c){
        if(n == 0){
            return;
        }
        move(n - 1, a, c, b);
        c.addLast(a.removeLast());   // 中间
        print();
        move(n - 1, b, a, c);

    }

    private static void print(){
        System.out.println("-------------");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

//    @Test
//    @DisplayName("array 测试通过")
//    public void test1() {
//        int[] a = {6, 5, 4, 3, 2, 1};
//        System.out.println(sum(30000));
//    }





}
