package com.itheima.pattern.adapter.class_adapter;

/**
 * @version v1.0
 * @ClassName: TFCardImpl
 * @Description: 适配者类
 * @Author: 黑马程序员
 */
public class TFCardImpl implements TFCard {

    public String readTF() {
        return "TFCard read msg ： hello word TFcard";
    }

    public void writeTF(String msg) {
        System.out.println("TFCard write msg :" + msg);
    }
}
