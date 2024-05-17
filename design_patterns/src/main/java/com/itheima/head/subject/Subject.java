package com.itheima.head.subject;

/**
 * @author wing
 * @create 2024/5/17
 */
public interface Subject {
    // 注册观察者
    void registerObserver(Observer o);
    // 移除观察者
    void removeObserver(Observer o);
    // 发送通知
    void notifyObserver();

}
