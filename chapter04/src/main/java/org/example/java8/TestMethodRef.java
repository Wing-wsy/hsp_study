package org.example.java8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author wing
 * @create 2024/1/19
 */
public class TestMethodRef {

    @Test
    public void test1(){
        // 以前方式1
        Consumer<String> con = (x) -> System.out.println(x);
        // 以前方式2
        PrintStream ps1 = System.out;
        Consumer<String> con2 = (x) -> ps1.println(x);

        // 方法引用方式1
        PrintStream ps = System.out;
        Consumer<String> con1 = ps::println;
        // 方法引用方式2
        Consumer<String> con3 = System.out::println;
        con3.accept("adcd");
    }

    @Test
    public void test2(){
        // 方式一
        Employee emp = new Employee("张三", 18, 9999.99);
        Supplier<String> sup = () -> emp.getName();
        String s = sup.get();
        System.out.println(s);
        // 方式二
        Supplier<Integer> sup1 = emp::getAge;
        Integer s1 = sup1.get();
        System.out.println(s1);
    }

    @Test
    public void test3(){
        // 之前
       Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
       // 使用引用(前提是compare方法参数和返回值要和Comparator抽象方法的参数和返回值保持一致)
       Comparator<Integer> com1 = Integer::compare;
    }
    @Test
    public void test4(){
        // 之前
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        // 使用引用
        BiPredicate<String, String> bp1 = String::equals;
    }
    @Test
    public void test5(){
        // 之前
        Supplier<Employee> sup = () -> new Employee();
        // 使用引用
        Supplier<Employee> sup1 = Employee::new;
        Employee employee = sup1.get();
        System.out.println(employee);
    }
    @Test
    public void test6(){
        // 之前
        Function<Integer, Employee> fun = (x) -> new Employee(x);
        // 使用引用
        Function<Integer, Employee> fun1 = Employee::new;
        Employee employee = fun1.apply(101);
        System.out.println(employee);
        BiFunction<Integer, Integer, Employee> bf = Employee::new;
    }

    @Test
    public void test7(){
        // 之前
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);
        // 使用引用
        Function<Integer, String[]> fun1 = String[]::new;
        String[] strs1 = fun1.apply(20);
        System.out.println(strs1.length);
    }


}
