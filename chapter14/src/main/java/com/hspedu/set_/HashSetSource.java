package com.hspedu.set_;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * @author wing
 * @create 2023/11/29
 * 源码解析
 */
@SuppressWarnings({"all"})
public class HashSetSource {

    @Test
    public  void getHashSetSource(){

        HashSet hashSet = new LinkedHashSet();

        hashSet.add(new Person("wing"));
        hashSet.add(new Person("li"));
        hashSet.add(new Person("liu"));
        System.out.println("11");

//        hashSet.add("java");
//        hashSet.add("php");
//        System.out.println("11");


//        HashMap hashMap = new HashMap();
//        System.out.println(hashMap.put("1", "one"));
//        System.out.println(hashMap.put("2", "two"));
//        System.out.println(hashMap.put("1", "one"));
//        System.out.println(hashMap.put("3", "three"));
//        for (Object o : hashMap.keySet()) {
//
//        }

    }
}

class Person{
    String name;
    Person(String name){
        this.name = name;
    }
    // 这里重新了hashCode方法，当不等于“liu”固定返回1，等于“liu”返回2，目的让wing和li落到同一个索引，liu单独落到一个索引
    @Override
    public int hashCode() {
        if(this.name.equals("liu")){
            return 2;
        }
        return 1;
    }
}
