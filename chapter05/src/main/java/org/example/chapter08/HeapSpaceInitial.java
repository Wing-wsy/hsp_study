package org.example.chapter08;

/**
 * 1. 设置堆空间大小的参数
 * -Xms 用来设置堆空间（年轻代+老年代）的初始内存大小
 *      -X：是 JVM 的运行参数
 *      ms：是 memory start
 * -Xmx 用来设置堆空间（年轻代+老年代）的最大内存大小
 *
 * 2. 默认堆空间大小
 *    初始内存大小：物理电脑内存大小 / 64
 *    最大内存大小：物理电脑内存大小 / 4
 *
 * 3. 手动设置：-Xms600m -Xmx600m
 *        开发中建议将初始值内存和最大的堆内存设置成相同的值
 *
 * 4.查看设置的参数：
 *        方式一：jps   /  jstat -gc 进程id
 *        方式二：-xx:+PrintGCDetails
 */
public class HeapSpaceInitial {

    public static void main(String[] args) {
        new HeapSpaceInitial().method1(2);
    }

    public void method1(int select){
        int num;
        switch (select){
            case 100:
                num = 10;
                break;
            case 500:
                num = 20;
                break;
            case 200:
                num = 30;
                break;
            default:
                num = 40;
        }
    }

    public void method2(String season){
        switch (season){
            case "SPRING":
                break;
            case "SUMMER":
                break;
            case "AUTUMN":
                break;
            case "WINTER":
                break;
        }
    }


}

class Order{
    int id;
    static String name;
}
