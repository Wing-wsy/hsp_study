package com.imooc.log.stack.chapter4.test1;

public class Outer {

    public void fun() {
        int i = 1;
        class Inner{ // 方法内部类 不能有访问修饰符，比如public
            public void print() {
                System.out.println("Method I=" + i);
            }
        }
    }

    public static void main(String[] args) {
//        Outer.Inner outer = new Outer().new Inner();
//        outer.fun();
    }
}
