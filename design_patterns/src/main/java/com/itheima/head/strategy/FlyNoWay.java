package com.itheima.head.strategy;

/**
 * @author wing
 * @create 2024/5/16
 */
public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("不会飞行。。。");
    }
}
