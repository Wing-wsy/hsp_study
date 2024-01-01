package com.hspedu.test01;

/**
 * @author wing
 * @create 2023/12/24
 * 演示B不用重写A接口的方法，因为它的父类AA已经重写
 */
public class B extends AA {
    @Override
    public void test(int index) {
    }
}
