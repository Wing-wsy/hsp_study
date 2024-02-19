package org.example.chapter02;

import org.junit.Test;

/**
 * @author wing
 * @create 2024/1/25
 */
public class PassiveUse {
    @Test
    public void test() {
//        System.out.println(Child.num1);
//        Parent[] p = new Parent[10];
        Parent p1 = new Parent();
    }
}

class Child extends Parent {
    public static int num1 = 1;

    static {
        System.out.println("Child类的初始化");
    }
}

class Parent {
    static {
        System.out.println("Parent类的初始化");
    }

    public static int num = 1;
}
