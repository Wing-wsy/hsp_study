package com.hspedu.collection_;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author wing
 * @create 2023/11/29
 */
public class CollectionFor {
    @SuppressWarnings({"all"})
    @Test
    public void strongFor() {

        ArrayList arrayList = new ArrayList();
        arrayList.add(new Teacher("王老师", 45, 1.68));
        arrayList.add(new Teacher("李老师", 25, 1.58));
        arrayList.add(new Teacher("刘老师", 27, 1.78));

        // 使用增强for循环，在Collection集合
        // 底层仍然是迭代器
        // 增强for循环，可以理解成简化版的迭代器
        // I快捷键
        for (Object o : arrayList) {
            System.out.println(o);
        }


        // 增强for循环也可以在数组使用
        int[] nums = {1, 8, 9, 10};
        for (int num : nums) {
            System.out.println(num);
        }

    }

    class Teacher {
        private String name;
        private int age;
        private double height;

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

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public Teacher(String name, int age, double height) {
            this.name = name;
            this.age = age;
            this.height = height;
        }

        @Override
        public String toString() {
            return "Teacher{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", height=" + height +
                    '}';
        }
    }
}
