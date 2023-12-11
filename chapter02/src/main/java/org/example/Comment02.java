package org.example;

/**
 * @author wing
 * @version 1.1
 *
 */
public class Comment02 {
    public static void main(String[] args) {
        Person p1 = new Person("wang",19);
        System.out.println("p1 的hashCode:" + p1.hashCode());
        System.out.println("p1 :" + p1);
        Person p2 = new Person("li",18);
        System.out.println("p2 的hashCode:" + p2.hashCode());
    }
}




class Person{
    String name;
    int age;
    public Person(String name,int age){
        this.age = age;
        this.name = name;
        System.out.println("this 的hashCode:" + this.hashCode());
        System.out.println("this:" + this);
    }
}
