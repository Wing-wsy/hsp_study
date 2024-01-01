package com.hspedu.list_;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
        LinkedHashMap map = new LinkedHashMap();
        map.put("1","11");
        map.put("2","22");
        map.put("3","33");
        Object o = map.get("2");
        System.out.println(o);
        System.out.println(map);
    }
}
