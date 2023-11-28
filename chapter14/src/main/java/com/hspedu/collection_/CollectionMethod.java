package com.hspedu.collection_;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2023/11/28
 */
public class CollectionMethod {

    @Test
    @SuppressWarnings({"all"})
    public void knowCollection() {
        //Collection
        //Map
        //LinkedHashMap
        //ArrayList
        // 创建一个ArrayList(单列集合)
        List list = new ArrayList();
        // add添加单个元素
        list.add("hello");
        list.add(10); // list.add(new Integer(10))
        list.add(true);
        list.add(new Integer(30));
        list.add(new String("word"));
        list.add(new String("word"));
        System.out.println(list);

        // remove删除元素
        list.remove(0);// 删除第一个元素hello
        list.remove("word"); //指定删除对象
        System.out.println(list);

        // contains查找某个元素是否存在
        System.out.println(list.contains(true));
        System.out.println(list.contains("hello"));

        // size获取元素个数
        System.out.println("size:" + list.size());

        // isEmpty是否为空
        System.out.println(list.isEmpty());

        // clear清空
        list.clear();
        System.out.println(list);

        // addAll 可以添加集合、多个元素
        List list2 = new ArrayList();
        list2.add(35.5d);
        list2.add(45.5f);
        list.addAll(list2);
        System.out.println(list);

        // containsAll 查找多个元素是否存在
        System.out.println(list.containsAll(list2));

        // removeall 删除多个元素
        List list3 = new ArrayList();
        list3.add("特别的爱特别的你");
        list.addAll(list3);
        System.out.println("removeall前:" + list);

        list.removeAll(list3);
        System.out.println("removeall后:" + list);

    }
}
