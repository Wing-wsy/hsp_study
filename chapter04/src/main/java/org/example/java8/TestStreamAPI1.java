package org.example.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream 的三个步骤
 * 1、创建 Stream
 * 2、中间操作
 * 3、终止操作（終端操作）
 */
public class TestStreamAPI1 {
    // 1、创建 Stream
    @Test
    public void test1() {
        // 1.可以通过 Collection 系列集合提供的stream（）或 parallelstream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2•通过 Arrays 中的静态方法 stream(）获取数组流
        Employee [] emps = new Employee [10];
        Stream<Employee> stream2 = Arrays.stream(emps);

        //3．通过 Stream 类中的静态方法 of(）
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //4．创建无限流
        // 迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
        stream4.limit(10).forEach(System.out::println);

        // 生成
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);
    }
}
