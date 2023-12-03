package com.hspedu.set_;

import java.util.LinkedHashSet;

/**
 * @author wing
 * @create 2023/12/3
 */
public class LinkedHashSetSource {

    public static void main(String[] args) {
        LinkedHashSet set = new LinkedHashSet<>();
        set.add("nihao");
        set.add("wang");
        set.add("nihao");
        set.add("li");
        System.out.println(set);
    }
}
