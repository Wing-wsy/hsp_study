package src.main.java.com.hspedu;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author wing
 * @create 2024/3/10
 */
public class TestClass {
    public static void main(String[] args) throws Exception {
        // 1. 获取到 Car 类对应的Class对象
        String classAllPath = "src.main.java.com.hspedu.Car";
        // 2. 输出 cls
        Class cls = Class.forName(classAllPath);
        System.out.println(cls);  // 显示 cls 是哪一个类的Class对象 class src.main.java.com.hspedu.Car
        System.out.println(cls.getClass()); // 输出 clas 运行类型：class java.lang.Class
        // 3. 包名
        System.out.println("包名：" + cls.getPackage().getName());
        // 4. 全类名
        System.out.println("全类名：" + cls.getName());
        // 5. 通过Class创建对象实例
        Car car = (Car)cls.newInstance();
        System.out.println(car.toString());
        // 6. 通过反射获取属性
        Field brand = cls.getField("brand");
        System.out.println("brand：" + brand.get(car));
        // 7. 通过反射给属性赋值
        brand.set(car,"奔驰");
        System.out.println("brand：" + brand.get(car));
        // 8. 通过反射获取全部属性
        Field[] fields = cls.getFields();
        for (Field field : fields) {
            System.out.print(field.getName() + ",");
        }
    }
}
