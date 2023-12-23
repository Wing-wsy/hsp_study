package com.hspedu.list_;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author wing
 * @create 2023/11/29
 */
public class List_ {

    @SuppressWarnings({"all"})
    @Test
    public void ListMethodExercise() {

        Set hashSet = new HashSet();
        hashSet.add("jack");
        hashSet.add("Wing");
        hashSet.add("Li");
        hashSet.add("jack"); // 不能添加
        hashSet.add(new Person("tom"));
        hashSet.add(new Person("tom"));

        // 经典面试题
        hashSet.add(new String("wsy"));
        hashSet.add(new String("wsy")); // 不能添加
        System.out.println(hashSet);

    }
}

class Person{
    public String name;
    Person(String name){
        this.name = name;
    }
}
