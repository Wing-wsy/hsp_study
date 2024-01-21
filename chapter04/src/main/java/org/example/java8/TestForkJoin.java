package org.example.java8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 计算0到1亿
 */
public class TestForkJoin {

    /*
     *  FrokJoin 框架
     */
    @Test
    public void test1() {
        // Java 1.8 时间
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 100000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());  // 8430
    }

    /*
     *  普通 for【累加数值越大，FrokJoin效果越明显，如果数值小，for速度比Frok还快，因为Frok拆分任务也需要时间】
     */
    @Test
    public void test2() {
        // Java 1.8 时间
        Instant start = Instant.now();
        long sum = 0L;

        for (long i = 0; i <= 100000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis()); // 31214
    }

    /*
     *  java8并行流 【FrokJoin用起来比较麻烦，java8进行了优化，效果比上面还快】
     */
    @Test
    public void test3() {
        // Java 1.8 时间
        Instant start = Instant.now();
        long sum = 0L;

        LongStream.rangeClosed(0, 100000000000L)
                .parallel()  // 并行流，底层还是 ForkJoin
                .reduce(0, Long::sum);

        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis()); // 5054
    }
}
