package com.itheima.pattern.head;

/**
 * @author wing
 * @create 2024/5/16
 */
public class Quack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("嘎嘎叫。。。");
    }
}
