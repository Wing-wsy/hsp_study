package src.main.java.com.hspedu.reflection.question;


import src.main.java.com.hspedu.Car;

/**
 * @author wing
 * @create 2024/3/10
 */
public class GetClass {
    public static void main(String[] args) throws Exception {
        // 1. Class.forName 方式
        String classAllPath = "src.main.java.com.hspedu.Car";
        Class cls1 = Class.forName(classAllPath);
        System.out.println(cls1);

        // 2. 类名.class，应用场景：用于参数传递
        Class cls2 = Car.class;
        System.out.println(cls2);

        // 3. 对象.getClass，应用场景：有对象实例
        Car car = new Car();
        Class cls3 = car.getClass();
        System.out.println(cls3);

        // 4. 通过类加载器来获取类的Class对象
        ClassLoader classLoader = car.getClass().getClassLoader();
        Class cls4 = classLoader.loadClass(classAllPath);
        System.out.println(cls4);

        // cls1、cls2、cls3、cls4 其实是同一个对象
        System.out.println(cls1.hashCode());
        System.out.println(cls2.hashCode());
        System.out.println(cls3.hashCode());
        System.out.println(cls4.hashCode());

        // 5. 基本数据类型
        Class<Integer> integerClass = int.class;
        Class<Boolean> booleanClass = boolean.class;
        System.out.println(integerClass);
        System.out.println(booleanClass);

        // 6. 基本数据类型包装类
        Class<Integer> integerClass1 = Integer.TYPE;
        Class<Boolean> booleanClass1 = Boolean.TYPE;
        System.out.println(integerClass1);
        System.out.println(booleanClass1);

        // 是同一个对象 int 和 Integer 是自动装箱和拆箱的
        System.out.println(integerClass.hashCode());
        System.out.println(integerClass1.hashCode());


    }
}
