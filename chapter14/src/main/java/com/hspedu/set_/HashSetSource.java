package com.hspedu.set_;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author wing
 * @create 2023/11/29
 * 源码解析
 */
@SuppressWarnings({"all"})
public class HashSetSource {

    @Test
    public  void getHashSetSource(){
        HashSet hashSet = new HashSet();
        hashSet.add("java");
        hashSet.add("php");
        hashSet.add("java");
        System.out.println("set=" + hashSet);

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
