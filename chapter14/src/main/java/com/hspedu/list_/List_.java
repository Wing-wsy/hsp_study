package com.hspedu.list_;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2023/11/29
 */
public class List_ {

    @SuppressWarnings({"all"})
    @Test
    public void ListMethodExercise() {
        List list = new ArrayList();
        list.add("张三丰");
        list.add("贾宝玉");

        // 插入一个元素
        list.add(1, "林黛玉");
        System.out.println(list);

        // 插入一个集合
        List list2 = new ArrayList();
        list2.add("Jack");
        list2.add("Tom");
        list.addAll(1, list2);
        System.out.println(list);

        // 获取集合中索引的值
        System.out.println("get(2):" + list.get(2)); // Tom

        // 返回首个出现的位置
        System.out.println(list.indexOf("林黛玉")); // 3

        // 返回末次出现的位置
        list.add("林黛玉");
        System.out.println(list.lastIndexOf("林黛玉")); // 5

        // 移除元素并且输出该元素
        list.remove(5);
        System.out.println(list);

        // 替换
        list.set(1, "王宝强");
        System.out.println(list);

        // 返回范围值内的子集合[0,3)所以只有0，1，2三个元素
        List listReturn = list.subList(0, 3);
        System.out.println(listReturn);
    }
}
