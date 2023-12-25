package com.hspedu.test;

/**
 * @author wing
 * @create 2023/12/24
 * 测试两个静态内部类的继承
 */
public class Animal {

    static class BasicInfo{
        final int age;
        final String name;
        BasicInfo(int age,String name){
            this.age = age;
            this.name = name;
        }
        @Override
        public String toString() {
            return "BasicInfo{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
