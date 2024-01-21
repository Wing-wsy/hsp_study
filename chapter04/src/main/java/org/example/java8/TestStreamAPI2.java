package org.example.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream 的三个步骤
 * 1、创建 Stream
 * 2、中间操作
 * 3、终止操作（終端操作）
 */
public class TestStreamAPI2 {
    // 2、中间操作
    /*
        筛选与切片
        filter一接收Lambda，从流中排除某些元素。
        1imit一截断流，使其元素不超过给定数量。
        skip(n）— 跳过元素，返回一个扔掉了前n 个元素的流。若流中元素不足n 个，则返回一个空流。与 1imit(n）互补
        distinct一筛选，通过流所生成元素的 hashcode(） 和equals(）去除重复元素
    */
    List<Employee> employees = Arrays.asList(
            new Employee("李四", 38, 5555.99),
            new Employee("王五", 50, 6666.66),
            new Employee("赵六", 16, 3333.33),
            new Employee("田七", 8, 7777.77),
            new Employee("田七", 8, 7777.77),
            new Employee("张三", 18, 1999.99)

    );

    //内部迭代：迭代操作由 stream API 完成
    @Test
    public void test1() {
        // 中间操作
        Stream<Employee> stream =employees.stream()
                .filter((e) -> {
                    System.out.println("中间操作");
                    return e.getAge() > 35;
                });

        // 终止操作
        stream.forEach(System.out::println);
    }

    //外部迭代
    @Test
    public void test2() {
        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    //limit，找到符合的条数后，就不再往下迭代，能提高效率
    @Test
    public void test3() {
        employees.stream()
                .filter((e) -> {
                    System.out.println("短路");
                    return e.getSalary() > 5000;
                })
                .limit(2)
                .forEach(System.out::println);
    }
    //skip 与 limit互补
    @Test
    public void test4() {
        employees.stream()
                .filter((e) -> {
                    System.out.println("短路");
                    return e.getSalary() > 5000;
                })
                .skip(1)
                .forEach(System.out::println);
    }
    // distinct 去重
    @Test
    public void test5() {
        employees.stream()
                .filter((e) -> {
                    return e.getSalary() > 5000;
                })
                .skip(1)
                .distinct()
                .forEach(System.out::println);
    }

    // map 映射
    @Test
    public void test6() {
        List<String> list = Arrays.asList("aaa","bbb","ccc");
        list.stream()
                .map((str) ->str.toUpperCase())
                .forEach(System.out::println);
        System.out.println("----------------------------");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
        System.out.println("----------------------------");

        // map处理流 // {{a,a,a},{b,b,b}...} 一个个流在一个大流中
        Stream<Stream<Character>> stream = list.stream()
                .map(TestStreamAPI2::filterCharacter);

        stream.forEach((sm) -> {
            sm.forEach(System.out::println);
            System.out.print("中间");
        });
        System.out.println("----------------------------");

        // flatMap处理流 //{a,a,a,b,b,b...}
        Stream<Character> sm = list.stream()
                .flatMap(TestStreamAPI2::filterCharacter);
        sm.forEach((s) -> {
            System.out.print(s);
            System.out.print("中间");
        });

    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    // sorted 排序
    @Test
    public void test7() {
        List<String> list = Arrays.asList("ddd","ccc","aaa","bbb");
        list.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("----------------------------");

        employees.stream()
                .sorted((e1,e2) -> {
                    return e1.getName().compareTo(e2.getName());
                })
                .forEach(System.out::println);

    }

}
