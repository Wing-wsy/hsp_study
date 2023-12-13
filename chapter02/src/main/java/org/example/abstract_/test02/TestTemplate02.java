package org.example.abstract_.test02;

/**
 * @author wing
 * @create 2023/12/13
 * 简单改进后的方法
 */
public class TestTemplate02 {
    /**
     * AA 和 BB 都有一个计算的job方法
     * 封装成一个 calculateTime 方法
     * 弊端：如果来个CC，依然要重复写 calculateTime 方法
     */

    public static void main(String[] args) {
       AA aa = new AA();
       aa.calculateTime();

       BB bb = new BB();
       bb.calculateTime();
    }
}
