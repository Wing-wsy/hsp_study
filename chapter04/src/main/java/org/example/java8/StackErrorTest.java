package org.example.java8;

/**
 * @author wing
 * @create 2024/1/26
 */
public class StackErrorTest {
    private static int count = 1;

    public static void main(String[] args) {
        System.out.println(count);  // 12774
        count++;
        main(args);
    }


}
