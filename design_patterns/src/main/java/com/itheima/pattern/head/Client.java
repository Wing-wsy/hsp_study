package com.itheima.pattern.head;

/**
 * 策略模式：定义了一个算法族，分别封装起来，使得它们之间可以互相变换。策略让算法的变化独立于使用它的客户。
 */
public class Client {
    public static void main(String[] args) {
        Duck duck = new MallardDuck();
        duck.performQuack();
        duck.performFly();
        duck.display();
        // 改变行为
        duck.setQuackBehavior(new Squack());
        duck.performQuack();
        duck.performFly();
        duck.display();
    }
}
