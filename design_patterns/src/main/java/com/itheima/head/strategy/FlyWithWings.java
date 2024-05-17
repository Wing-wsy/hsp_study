package com.itheima.head.strategy;

/**
 * @author wing
 * @create 2024/5/16
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("飞行。。。");
    }
}
