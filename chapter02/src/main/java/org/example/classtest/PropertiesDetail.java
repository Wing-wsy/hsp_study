package org.example.classtest;


/**
 * @author wing
 * @create 2023/12/7
 */
public class PropertiesDetail {
    public static void main(String[] args) {
        Person p1 = new Person();
        System.out.println(p1);
    }
}

class Person{
    String name;
    int age;
    double sal;
    byte byteTest;
    long height;
    short testShort;
    float floatTest;
    char sex;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sal=" + sal +
                ", byteTest=" + byteTest +
                ", height=" + height +
                ", testShort=" + testShort +
                ", floatTest=" + floatTest +
                ", sex=" + sex +
                '}';
    }
}
