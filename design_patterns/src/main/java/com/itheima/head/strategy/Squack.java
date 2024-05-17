package com.itheima.head.strategy;

/**
 * @author wing
 * @create 2024/5/16
 */
public class Squack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("吱吱叫。。。");
    }
}
