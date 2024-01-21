package org.example.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author wing
 * @create 2024/1/19
 */
public class TestLambda2 {

    // 语法格式一：无参数，无返回值
    @Test
    public void test1(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi");
            }
        };
        r.run();
        System.out.println("---------------");
        Runnable r1 = () -> System.out.println("Hello");
        r1.run();
    }
    // 语法格式二：有一个参数，无返回值
    @Test
    public void test2(){
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("hello");
    }

    // 语法格式三：有一个参数，小括号可以省略不写
    @Test
    public void test3(){
        Consumer<String> con = x -> System.out.println(x);
        con.accept("hi");
    }
    // 语法格式四：有两个以上的参数，省返回值，并且Lambda 体中有多条语句
    @Test
    public void test4(){
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }
    // 语法格式五：若Lambda 体中只有一条语句，return 和 大括号都可以省略不写
    @Test
    public void test5(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
    }
    // 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断“
    @Test
    public void test6(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> com1 = (Integer x, Integer y) -> Integer.compare(x, y);
    }

    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }
    @Test
    public void test7(){
        happy(1000, (m) -> System.out.println("消费金额：" + m));
    }

    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(sup.get());
        }
        return list;
    }
    @Test
    public void test8(){
        List<Integer> numList = getNumList(10, () -> (int)(Math.random() * 100));
        for (Integer num : numList) {
            System.out.println(num);
        }
    }

    public String strHandler(String str, Function<String,String> fun) {
        return fun.apply(str);
    }
    @Test
    public void test9(){
        String upper = strHandler("hello", (str) -> str.toUpperCase());
        System.out.println(upper);
    }

    public List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();
        for (String str : list) {
            if (pre.test(str)) {
                strList.add(str);
            }
        }
        return strList;
    }
    @Test
    public void test10(){
        List<String> list = Arrays.asList("hello", "hi", "Wing", "Lambda");
        List<String> str = filterStr(list, (s) -> s.length() > 4);
        for (String s : str) {
            System.out.println(s);
        }
    }


}
