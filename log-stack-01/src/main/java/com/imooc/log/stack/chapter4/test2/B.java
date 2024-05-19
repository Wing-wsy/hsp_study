package com.imooc.log.stack.chapter4.test2;

import com.imooc.log.stack.chapter4.test1.A;

public class B extends A{
    public B(String strE) {
        super(strE);
    }

    public static void main(String[] args) {
        B b = new B("eee");
        System.out.println(b.strE);
        b.funA();
//        System.out.println(b.strA + "," + b.strB);
//        b.func();
    }

    public void func(){
//        A a = new A();
//        System.out.println(super.strB);
//        super.strB = "heihei";
//        System.out.println(super.strB);

    }

    public void funA() {

    }
}
