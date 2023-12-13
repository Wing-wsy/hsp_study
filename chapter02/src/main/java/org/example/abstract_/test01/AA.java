package org.example.abstract_.test01;

/**
 * @author wing
 * @create 2023/12/13
 */
public class AA {
    
    public void job(){

        long start = System.currentTimeMillis();
        long num = 0;
        for (long i = 0; i < 800000; i++) {
            num += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("AA执行时间：" + (end - start));

    }
}
