package com.hspedu.collection_;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author wing
 * @create 2023/12/20
 */
public class CollectionTest01 {
    public static void main(String[] args) {
        ArrayList1<Person> arrayList1 = new ArrayList1<>();
        ArrayList1<Person> personList = new ArrayList1<>();
        ArrayList1<String> strList = new ArrayList1<>();
        ArrayList1<Stu> stuList = new ArrayList1<>();
        ArrayList1<Tea> teaList = new ArrayList1<>();

        arrayList1.addAll(personList); // ok
//        arrayList1.addAll(strList); // 错误
        arrayList1.addAll(stuList); // ok
        arrayList1.addAll(teaList); // ok

        /* 测试集合里面的方法 */
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person()); // ok
        list.add(new Stu()); // ok
        list.add(new Tea()); // ok
    }
}

interface Collection1<E>{
    boolean addAll(Collection1<? extends E> c);
//    boolean addAll(Collection1<E> c);
}
class ArrayList1<E> implements Collection1<E>{
    @Override
    public boolean addAll(Collection1<? extends E> c) {
//    public boolean addAll(Collection1<E> c) {
        return false;
    }
}
class Person{}
class Stu extends Person{}
class Tea extends Person{}