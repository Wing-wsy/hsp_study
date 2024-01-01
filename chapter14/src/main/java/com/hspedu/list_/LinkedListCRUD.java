package com.hspedu.list_;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author wing
 * @create 2023/11/29
 */
@SuppressWarnings({"all"})
public class LinkedListCRUD{


    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        map.put("1","1");
        map.put("1","11");
        map.putIfAbsent("1","111");
        map.put("2","1");
        map.put("3","1");
        map.put("4","1");
        map.put("5","1");
        map.put("6","1");
        map.put("7","1");
        map.put("8","1");
        map.put("9","1");
        map.put("10","1");
        map.put("11","1");
        map.put("12","1");
        map.put("abc","1");
        map.put("14","1");
        map.put("15","1");
        map.put("16","1");
        map.put("17","1");
        Set<Map.Entry<String, String>> entries = map.entrySet();


        System.out.println(map);
    }
}
