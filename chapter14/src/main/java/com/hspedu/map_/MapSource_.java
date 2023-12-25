package com.hspedu.map_;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wing
 * @create 2023/12/23
 */
public class MapSource_ {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("no1","Wing");
        map.put("no2","Li");

        Set set = map.entrySet();
        for (Object obj : set) {
            System.out.println(obj.getClass());
            System.out.println(obj);
            // 为了从 HashMap$Node 取出k-V
            // 1．先做一个向下转型
            Map.Entry entry = (Map.Entry)obj;
            // 2. 这样就能轻松获取到k和v
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        // 只是获取key
        Set set1 = map.keySet();
        for (Object k : set1) {
            System.out.println("只获取k：" + k);
        }
        System.out.println("k类型" + set1.getClass());
        // 只是获取value
        Collection values = map.values();
        for (Object v : values) {
            System.out.println("只获取v：" + v);
        }
        System.out.println("v类型" + values.getClass());
    }
}
