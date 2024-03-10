package org.example.chapter08;

/**
 * 成员变量（非静态）的赋值过程：
 * 1.默认初始化
 * 2.显示初始化/代码块初始化
 * 3.构造器初始化
 */



class Father {
    int x = 10;
    public Father(){
        this.print();
        x = 20;
    }
    public void print(){
        System.out.println("Father.x = " + x);
    }
}

class Son extends Father {
    int x = 30;
    public Son(){
        this.print();
        x = 40;
    }
    public void print(){
        System.out.println("Son.x = " + x);
    }
}

public class SonTest {
    public static void main(String[] args) {
        Father f = new Son();
        System.out.println(f.x);  // 属性没有重写，所以f.x肯定就是父类的
    }
}