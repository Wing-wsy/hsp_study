package com.hspedu.list_;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @author wing
 * @create 2023/11/29
 * List 集合遍历方式
 */
public class ListFor {

    @Test
    public void getBianli() {
        List list = new ArrayList();
//        List list = new Vector();
//        List list = new LinkedList();
        list.add("Jack");
        list.add("Tom");
        list.add("鱼香肉丝");
        list.add("北京烤鸭");
        list.add("北京烤鸭");
        list.add("北京烤鸭");
        list.add("北京烤鸭");

        // 迭代器遍历
        System.out.println("-----迭代器遍历:-----");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }

        // 增强for循环遍历
        System.out.println("------增强for循环遍历:-------");
        for (Object o : list) {
            System.out.println(o);
        }

        // 普通for循环遍历(类似数组)
        System.out.println("-----普通for循环遍历:-------");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }
}
