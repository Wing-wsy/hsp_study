package com.hspedu.list_;

import com.hspedu.test01.A;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wing
 * @create 2023/11/29
 */
public class LinkedListCRUD {

    @Test
    public void linkedListCRUD() {

       List one = new ArrayList();
       one.add("1");
       one.add("2");
       one.add("3");
       one.add("4");
       System.out.println(one);
       System.out.println(one.getClass());

       List two = one.subList(1, 3);
       System.out.println(two);
       System.out.println(two.getClass());
       two.set(0,"a");
       System.out.println(two);
       System.out.println(one);
       List three = two.subList(1, 2);
       System.out.println(three);
       System.out.println(three.getClass());
       three.set(0,"b");
       System.out.println(three);
       System.out.println(two);
       System.out.println(one);


    }
}
