package com.itheima.head.strategy;

/**
 * @author wing
 * @create 2024/5/16
 */
public class MallardDuck extends Duck {
    public MallardDuck(){
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }
    @Override
    void display() {
        System.out.println("我是MallardDuck ");
    }
}
