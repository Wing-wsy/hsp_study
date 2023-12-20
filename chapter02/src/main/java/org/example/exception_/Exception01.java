package org.example.exception_;


import java.util.ArrayList;

/**
 * @author wing
 * @create 2023/12/17
 */
public class Exception01 {

    public static void main(String[] args) {
        Person<String> p = new Person<>("Wing");
        p.show();
        Person<Integer> p1 = new Person<>(100);
        p1.show();
        Person<A> p2 = new Person<>(new A());
        p2.show();
        Person<A> p3 = new Person<>(new B());
        p3.show();
    }
}

class A{}
class B extends A{}
class Person<E>{
    E s;
    Person(E s){
        this.s = s;
    }
    public void show(){
        System.out.println(s + ",运行类型：" + s.getClass());
    }
}


