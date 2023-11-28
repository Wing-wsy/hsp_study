package com.hspedu.collection_;

import org.junit.Test;

/**
 * @author wing
 * @create 2023/11/28
 * 数组扩容:灵活性差，比较麻烦
 */
public class ArrayExample {

    @Test
    public void expandArray() {
        Person[] people = new Person[3];
        // 添加几个元素进去
        people[0] = new Person("小明", 10);
        people[1] = new Person("小刚", 20);
        people[2] = new Person("小红", 30);

        for (Person person : people) {
            System.out.println(person);
        }

        System.out.println("-----------扩容后----------");

        Person[] peopleAdd = new Person[5];
        for (int i = 0; i < people.length; i++) {
            peopleAdd[i] = people[i];
        }

        peopleAdd[3] = new Person("梅梅", 40);
        peopleAdd[4] = new Person("兰兰", 50);

        for (Person person : peopleAdd) {
            System.out.println(person);
        }
    }


    class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
