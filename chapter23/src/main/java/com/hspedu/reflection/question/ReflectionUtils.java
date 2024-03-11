package src.main.java.com.hspedu.reflection.question;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wing
 * @create 2024/3/10
 */
public class ReflectionUtils {
    public static void main(String[] args) throws Exception {
        Class<?> personCls = Class.forName("src.main.java.com.hspedu.reflection.question.Person");
        //获取全类名
        System.out.println("全类名："+personCls.getName());
        //获取简单类名
        System.out.println("简单类名："+personCls.getSimpleName());
        //获取所有public修饰的属性，包含本类以及父类的
        Field[] fields = personCls.getFields();
        for (Field field : fields) {
            System.out.println("所有public属性包括父类的："+field.getName());
        }
        //获取所有权限的属性，包含本类以及父类
        Field[] declaredFields = personCls.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("所有权限属性包括父类的："+declaredField.getName());
        }
        //获取所有public修饰的方法，包含本类以及父类的（全部父类）
        Method[] methods = personCls.getMethods();
        for (Method method : methods) {
            System.out.println("所有public方法包括父类的："+method.getName());
        }
        //获取所有权限修饰的方法，包含本类以及父类的（全部父类）
        Method[] declaredMethods = personCls.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println("所有权限方法包括父类的："+method.getName());
        }
        //其他不再一一下，看API文档就行
        //获取本类所有构造器
        //获取包信息
        //获取父类信息
        //获取接口信息
    }
}

class A {
    public String hobby;
    public void hi(){}
}

class Person extends A{
    public String name;
    protected int age;
    String job;
    private double sal;
    public void m1(){

    }
    protected void m2(){

    }
    void m3(){

    }
    private void m4(){

    }

}