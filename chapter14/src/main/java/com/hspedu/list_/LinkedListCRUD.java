package com.hspedu.list_;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author wing
 * @create 2023/11/29
 */
public class LinkedListCRUD {

    @Test
    public void linkedListCRUD() {
        LinkedList list = new LinkedList();
        list.add(100);
        list.add(200);
        list.add(300);
        System.out.println(list);

        // 修改，把200更改成400
        System.out.println(list.set(1, 400)); // 200

        // 删除400
        System.out.println(list.remove(1)); // 400

        // 查找第2个元素
        System.out.println(list.get(1));

        // 迭代器遍历
        System.out.println("----迭代器遍历----");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }

        System.out.println("增强for循环遍历");
        for (Object o : list) {
            System.out.println(o);
        }

        System.out.println("普通for循环遍历");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }
}
