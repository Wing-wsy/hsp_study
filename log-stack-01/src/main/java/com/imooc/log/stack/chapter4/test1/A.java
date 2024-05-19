package com.imooc.log.stack.chapter4.test1;

public class A {

    // 所有包中能访问
    public String strA = "strA";
    // 不同包的子类访问
    protected String strB = "strB";
    // 相同包下访问
    String strC = "strC";
    // 本类中访问
    private String strD = "strD";
    public final String strE;

    public A(String strE){
        this.strE = strE;
    }

    private final void funA(){
        System.out.println("funA...");
    }

    public static void main(String[] args) {
//        A a = new A();
//        System.out.println(a.strA + "," + a.strB+ "," + a.strC + "," + a.strD);
    }

}
