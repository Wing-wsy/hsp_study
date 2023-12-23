package com.hspedu.collection_;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author wing
 * @create 2023/11/29
 */
public class CollectionIterator {


    @Test
    @SuppressWarnings({"all"})
    public void getItrator() {
        Collection arrayList = new ArrayList();
        arrayList.add(new Book("三国演义", "罗贯中", 42.99));
        arrayList.add(new Book("西游记", "吴承恩", 38.5));
        arrayList.add(new Book("水浒传", "施耐庵", 66.66));

        // ceylon
        System.out.println("直接输出:" + arrayList);
        System.out.println("------------------");

        // 先得到对应的迭代器
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) { // 判断是否还有数据
            // 返回下一个元素，类型是Object
            Object next = iterator.next();
            System.out.println(next);
        }

        // 当退出while循环后,这时迭代器指向最后一个元素
        //报错 iterator.next(); // java.util.NoSuchElementException

        arrayList.add("今天天气不错");
        System.out.println("重置迭代器之后………………");
        // 注意这里重置了
        iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }
    }


    class Book {
        private String name;
        private String author;
        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Book(String name, String author, double price) {
            this.name = name;
            this.author = author;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
