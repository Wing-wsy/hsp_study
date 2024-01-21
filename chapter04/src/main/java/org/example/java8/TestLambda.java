package org.example.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * @author wing
 * @create 2024/1/19
 */
public class TestLambda {

    // 原来的匿名内部类
    @Test
    public void test1(){
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> ts = new TreeSet<>(com);
    }
    // Lambda 表达式
    @Test
    public void test2(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.99),
            new Employee("王五", 50, 6666.66),
            new Employee("赵六", 16, 3333.33),
            new Employee("田七", 8, 7777.77)
    );

    // 需求：获取当前公司中员工年龄大于 35 的员工信息
    public List<Employee> filterEmployees(List<Employee> list) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list) {
            if (emp.getAge() >= 35) {
                emps.add(emp);
            }
        }
        return emps;
    }
    // 需求：获取当前公司中员工工资大于 5000 的员工信息
    public List<Employee> filterEmployees2(List<Employee> list) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list) {
            if (emp.getSalary() >= 6000) {
                emps.add(emp);
            }
        }
        return emps;
    }

    @Test
    public void test3(){
        // 先过滤年龄大于 35
        List<Employee> list = filterEmployees(employees);
        // 再过滤工资大于 5000
        List<Employee> list1 = filterEmployees2(list);
        for (Employee employee : list1) {
            System.out.println(employee);
        }
    }

    // 优化方式一：
    public List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> mp) {
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : list) {
            if (mp.test(employee)) {
                emps.add(employee);
            }
        }
        return emps;
    }
    @Test
    public void test4(){
        // 先过滤年龄大于 35
        List<Employee> list = filterEmployee(employees, new FilterEmployeeByAge());
        // 再过滤工资大于 5000
        List<Employee> list1 = filterEmployee(list, new FilterEmployeeBySalary());
        for (Employee employee : list1) {
            System.out.println(employee);
        }
    }
    @Test
    public void test5(){
        // 先过滤年龄大于 35
        List<Employee> list = filterEmployee(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee t) {
                return t.getAge() >= 35;
            }
        });
        // 再过滤工资大于 5000
        List<Employee> list1 = filterEmployee(list, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee t) {
                return t.getSalary() >= 5000;
            }
        });
        for (Employee employee : list1) {
            System.out.println(employee);
        }
    }

    @Test
    public void test6(){
        // 先过滤年龄大于 35
        List<Employee> list = filterEmployee(employees, (e) -> e.getAge() >= 35);
        // 再过滤工资大于 5000
        List<Employee> list1 = filterEmployee(list, (e) -> e.getSalary() >= 5000);
        list1.forEach(System.out::println);
    }

    @Test
    public void test7(){
        employees.stream()
                .filter((e) -> e.getAge() >= 35)
                .filter((e) -> e.getSalary() >= 6000)
                .limit(2)
                .forEach(System.out::println);
    }
    @Test
    public void test8(){
        Collections.sort(employees, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    // 需求：用于处理字符串
    public String strHandler(String str, MyFunction mf) {
        return mf.getValue(str);
    }

    @Test
    public void test9(){
        String trimStr = strHandler("\t\t\t hello", (str) -> str.trim());
        System.out.println(trimStr);

        String upper = strHandler("hello", (str) -> str.toUpperCase());
        System.out.println(upper);

        String newStr = strHandler("hello", (str) -> str.substring(2,4));
        System.out.println(newStr);
    }

















}
