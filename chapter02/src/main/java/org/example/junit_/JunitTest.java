package org.example.junit_;

import org.junit.jupiter.api.Test;

/**
 * @author wing
 * @create 2023/12/20
 */
public class JunitTest {
    public static void main(String[] args) {
        // 传统测试
        //JunitTest junitTest = new JunitTest();
        //junitTest.m1();
        //junitTest.m2();
    }

    @Test
    public void m1(){
        System.out.println("m1被调用");
    }
    @Test
    public void m2(){
        System.out.println("m2被调用");
    }
}
