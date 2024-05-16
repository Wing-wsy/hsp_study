package com.itheima.pattern.proxy.jdk_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wing
 * @create 2024/5/15
 */
public class ProxyFactory1 implements InvocationHandler {
    private Object factory ; //被代理对象的引用
    public Object getFactory(){
        return factory;
    }
    public void setFactory(Object factory){
        this.factory = factory;
    }
    //反射执行方法
    //1.Object ：jdk创建的代理类，无需赋值
    //2.Method : 目标类当中的方法，jdk提供，不需要赋值
    //3.Object[]：目标类当中的方法参数 ，jdk提供，不需要赋值
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代售点收取一定的服务费用(jdk动态代理)");
        method.invoke(factory, args);//执行目标类当中的方法
        System.out.println("售后服务(jdk动态代理)");
        return null;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(factory.getClass().getClassLoader(), factory.getClass().getInterfaces(), this);
    }

}
