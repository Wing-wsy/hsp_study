package org.example.abstract_.test02;

/**
 * @author wing
 * @create 2023/12/13
 */
public class BB {
    public void calculateTime(){
        long start = System.currentTimeMillis();
        job();
        long end = System.currentTimeMillis();
        System.out.println("BB执行时间：" + (end - start));
    }
    public void job(){
        long num = 0;
        for (long i = 0; i < 100000; i++) {
            num *= i;
        }
    }
}
