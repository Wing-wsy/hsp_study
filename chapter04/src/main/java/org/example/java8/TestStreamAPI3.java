package org.example.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 终止操作（終端操作）
 */
public class TestStreamAPI3 {
    List<Employee> employees = Arrays.asList(
            new Employee("李四", 38, 5555.99, Employee.Status.FREE),
            new Employee("王五", 50, 6666.66, Employee.Status.BUSY),
            new Employee("赵六", 16, 3333.33, Employee.Status.VOVATION),
            new Employee("田七", 8, 7777.77, Employee.Status.FREE),
            new Employee("田七", 8, 7777.77, Employee.Status.BUSY),
            new Employee("张三", 18, 1999.99, Employee.Status.FREE)

    );
    /*
        查找与匹配
        a11Match-检查是否匹配所有元素
        anyMatch—检查是否至少匹配一个元素
        noneMatch-检查是否没有匹配所有元素
        findFirst一返回第一个元素
        findAny一返回当前流中的任意元素【不研究】
        count-返回流中元素的总个数
        max—返回流中最大值
        min一返回流中最小值
    */

    @Test
    public void test1() {
        // a11Match-检查是否匹配所有元素
        boolean b1 = employees.stream()
                .allMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        // anyMatch—检查是否至少匹配一个元素
        boolean b2 = employees.stream()
                .anyMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);

        // noneMatch-检查是否没有匹配所有元素
        boolean b3 = employees.stream()
                .noneMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b3);

        // findFirst一返回第一个元素
        // 放到Optional避免为空，因为findFirst有可能获取不到
        Optional<Employee> op = employees.stream()
                .sorted((e1, e2) -> -Double.compare(e1.getSalary(),e2.getSalary()))
                .findFirst();
        System.out.println(op.get());
    }

    @Test
    public void test2() {
        // count-返回流中元素的总个数
        long count = employees.stream()
                .count();
        System.out.println(count);

        // max—返回流中最大值的员工信息
        Optional<Employee> op = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(),e2.getSalary()));
        System.out.println(op.get());

        // min一返回流中最小值的工资
        Optional<Double> op1 = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(op1.get());

    }

    @Test
    public void test3() {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Integer sum = list.stream()
                .reduce(0, (x,y) -> x + y);
        System.out.println(sum);

        System.out.println("----------------");

        // 可能为空的值封装到Optional，避免空指针
        Optional<Double> op = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op.get());
    }

    @Test
    public void test4() {
        //取出所有的名字放到List集合中
        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);
        System.out.println("----------------");

        //取出所有的名字放到Set集合中,可以过滤重复
        Set<String> list1 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        list1.forEach(System.out::println);
        System.out.println("----------------");

        //取出所有的名字放到HashSet集合中,可以过滤重复
        HashSet<String> list2 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        list2.forEach(System.out::println);

    }

    @Test
    public void test5() {
        // 总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);
        System.out.println("----------------");

        // 平均值
        Double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);
        System.out.println("----------------");

        // 总和
        Double sum = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);
        System.out.println("----------------");

        // 最大值
        Optional<Employee> max = employees.stream()
                .collect(Collectors.maxBy((e1,e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());
        System.out.println("----------------");

        // 最小值
        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());
    }

    @Test
    public void test6() {
        Integer[] nums = new Integer[]{1,2,3,4,5};
        Arrays.stream(nums)
                .map((x) -> x * x)
                .forEach(System.out::println);
    }
    @Test
    public void test7() {
        Optional<Integer> count = employees.stream()
                .map((e) -> 1)
                .reduce(Integer::sum);
        System.out.println(count.get());

    }

}
