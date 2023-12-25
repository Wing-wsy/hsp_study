package com.hspedu.map_;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wing
 * @create 2023/12/23
 */
public class Map_ {

    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("no1","Wing");
        map.put("no2","Li");
        map.put("no1","xiao");
        Set set = map.entrySet();
        System.out.println(set);

    }
}
