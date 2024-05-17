package com.itheima.pattern.observable;

import java.util.Observable;

/**
 * @author wing
 * @create 2024/5/16
 */
public class Thief extends Observable {
    private String name;
    public Thief(String name) {
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void steal() {
        System.out.println("小偷：我偷东西了，有没有人来抓我！！！");
        super.setChanged(); //changed  = true
        super.notifyObservers();
    }
}
