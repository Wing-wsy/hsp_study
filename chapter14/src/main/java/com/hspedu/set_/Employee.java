package com.hspedu.set_;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author wing
 * @create 2023/11/30
 * 练习要求：
 * 1、创建3个Workers放入HashSet 中
 * 2、当name和age一样时就不能添加到HashSet
 */
public class Employee {

    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        hashSet.add(new Workers("小明", 10));
        hashSet.add(new Workers("小红", 20));
        hashSet.add(new Workers("小明", 10));
        System.out.println(hashSet);
    }
}

class Workers {
    private String name;
    private int age;

    public Workers(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Workers{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workers workers = (Workers) o;
        return age == workers.age && Objects.equals(name, workers.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
