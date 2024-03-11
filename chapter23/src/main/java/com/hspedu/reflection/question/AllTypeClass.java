package src.main.java.com.hspedu.reflection.question;


import java.io.Serializable;

/**
 * @author wing
 * @create 2024/3/10
 */
public class AllTypeClass {
    public static void main(String[] args) throws Exception {
        Class<String> c1 = String.class;// 外部类
        Class<Serializable> c2 = Serializable.class;//接口
        Class<Integer[]> c3 = Integer[].class;//数组
        Class<float[][]> c4 = float[][].class;//二维数组
        Class<Deprecated> c5 = Deprecated.class;//注解
        Class<Thread.State> c6 = Thread.State.class;//枚举
        Class<Long> c7 = long.class;//基本数据类型
        Class<Void> c8 = void.class;//void数据类型
        Class<Class> c9 = Class.class;
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);
        System.out.println(c6);
        System.out.println(c7);
        System.out.println(c8);
        System.out.println(c9);
    }
}
