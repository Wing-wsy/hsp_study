package com.itheima.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @version v1.0
 * @ClassName: SubscriptionSubject
 * @Description: 具体主题角色类
 * @Author: 黑马程序员
 */
public class SubscriptionSubject implements Subject {

    //定义一个集合，用来存储多个观察者对象
    private List<Observer> objList = new ArrayList<Observer>();

    public void attach(Observer observer) {
        objList.add(observer);
    }

    public void detach(Observer observer) {
        objList.remove(observer);
    }

    public void notify(String message) {
        //遍历集合
        for (Observer observer : objList) {
            //调用观察者对象中的update方法
            observer.update(message);
        }
    }
}
