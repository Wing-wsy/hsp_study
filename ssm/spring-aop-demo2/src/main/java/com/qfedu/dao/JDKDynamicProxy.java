package com.qfedu.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wing
 * @create 2024/5/27
 */
public class JDKDynamicProxy implements InvocationHandler {

    private Object obj;
    public JDKDynamicProxy(Object obj) {
        this.obj = obj;
    }

    public Object getProxy() {
        Object object = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
        return object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("打印入参");
        Object res =  method.invoke(obj,args);
        return res;
    }
}
