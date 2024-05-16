package com.itheima.pattern.head;

/**
 * @author wing
 * @create 2024/5/16
 */
public class MuteQuack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("不出声。。。");
    }
}
