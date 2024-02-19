package org.example.chapter02;

/**
 * @author wing
 * @create 2024/2/8
 */
public class ConstClass {

    static {
        System.out.println("ConstClass init");
    }
    public static final String HELLO = "hello";
    public static String HELLO1= "hello";

}

class TestConstClass {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO);
        System.out.println(ConstClass.HELLO1);
    }
}
