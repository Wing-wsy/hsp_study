package com.hspedu.set_;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author wing
 * @create 2023/11/30
 * 练习要求：
 * 1、Employ 里只要名字name和出生年份birthday相同就不能添加到HashSet
 */
public class Employee02 {
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        hashSet.add(new Employ("小明", 10000.88d, new MyDate("1996", "01", "01")));
        // 姓名一样，出生年份不同
        hashSet.add(new Employ("小明", 10001.88d, new MyDate("1997", "01", "01")));
        // 出生年份和工资不同
        hashSet.add(new Employ("小明", 10002.88d, new MyDate("1996", "01", "01")));
        // 工资和第一个比不同(按照规则，这个应该不展示)
        hashSet.add(new Employ("小明", 10002.88d, new MyDate("1996", "01", "01")));

        // 重写了toString()方法，这里可以遍历一下，看看值
        System.out.println("size:" + hashSet.size());
        for (Object o : hashSet) {
            System.out.println(o);
        }
    }
}

class Employ {
    private String name;
    private double sal;
    private MyDate birthday;

    public Employ(String name, double sal, MyDate birthday) {
        this.name = name;
        this.sal = sal;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employ employ = (Employ) o;
        return Objects.equals(name, employ.name) && Objects.equals(birthday, employ.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday);
    }
    @Override
    public String toString() {
        return "Employ{" +
                "name='" + name + '\'' +
                ", sal=" + sal +
                ", birthday=" + birthday +
                '}';
    }
}


class MyDate {
    String year;
    String month;
    String day;

    public MyDate(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDate myDate = (MyDate) o;
        return Objects.equals(year, myDate.year) && Objects.equals(month, myDate.month) && Objects.equals(day, myDate.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}