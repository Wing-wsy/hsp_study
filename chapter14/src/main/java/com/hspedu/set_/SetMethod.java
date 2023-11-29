package com.hspedu.set_;

import org.junit.Test;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wing
 * @create 2023/11/29
 */
public class SetMethod {

    @Test
    public void SetMethodEx() {
        // 以Set接口的实现类，HashSet为例
        // Set接口的实现类的对象(Set接口对象)不能存放重复元素
        // 可以添加一个null
        // Set接口对象存放数据、对象是无序的(添加顺序和取出顺序不一致)
        // 取出的顺序是固定的
        Set set = new HashSet();
        set.add("小明");
        set.add("小芳");
        set.add("小刚");
        set.add("小明");
        set.add(null);
        set.add(null);
        System.out.println(set);

        // 遍历
        //方式1；迭代器
        System.out.println("---迭代器---");
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }

        // 增强for循环
        System.out.println("---增强for循环---");
        for (Object o : set) {
            System.out.println(o);
        }

        System.out.println("size:" + set.size());
        System.out.println("是否为空:" + set.isEmpty());
        System.out.println("是否包含元素[小刚]:" + set.contains("小刚"));
        System.out.println("remove:" + set.remove("小刚"));
        System.out.println("remove:" + set.remove(null));
        System.out.println(set);
    }
}
