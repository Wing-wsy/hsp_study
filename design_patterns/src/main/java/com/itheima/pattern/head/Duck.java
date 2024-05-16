package com.itheima.pattern.head;

/**
 * @author wing
 * @create 2024/5/16
 */
public abstract class Duck {
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;
    public void setFlyBehavior(FlyBehavior fb){
        flyBehavior = fb;
    }
    public void setQuackBehavior(QuackBehavior qb){
        quackBehavior = qb;
    }
    public void swim(){
        System.out.println("游泳。。。");
    }
    public void performFly(){
        flyBehavior.fly();
    }
    public void performQuack(){
        quackBehavior.quack();
    }
    abstract void display();

}
