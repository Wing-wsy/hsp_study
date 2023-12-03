package org.example;

/**
 * @author wing
 * @create 2023/12/3
 * 演示转义字符的使用
 */
public class ChangeChar {

    /*
        1. \t：一个制表位，实现对齐功能
        2. \n：换行符
        3. \\：一个\
        4. \"：一个"
        5. \'：一个'
        6. \r：一个回车
     */

    public static void main(String[] args) {
        // 1. \t：一个制表位，实现对齐功能
        System.out.println("北京\t深圳\t广州");
        // 2. \n：换行符
        System.out.println("北京\n深圳\n广州");
        // 3. \\：一个\
        System.out.println("北京\\深圳\\广州");
        // 4. \ "：一个"
        System.out.println("北京\"深圳\"广州");
        // 5. \'：一个'
        System.out.println("北京\'深圳\'广州");

        // 6. \r：一个回车
        System.out.println("今天吃什么饭\r深圳");
        System.out.println("今天吃什么饭\r\n深圳");

    }
}
