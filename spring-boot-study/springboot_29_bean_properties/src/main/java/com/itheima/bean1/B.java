package com.itheima.bean1;

import org.springframework.stereotype.Component;

/**
 * @author wing
 * @create 2024/6/8
 */
@Component
public class B {
    private A a ;

    public B(A a) {
        this.a = a;
        System.out.println("a的名字：" + a.getName());
    }
}
