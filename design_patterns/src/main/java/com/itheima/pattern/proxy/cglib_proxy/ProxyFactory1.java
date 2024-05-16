package com.itheima.pattern.proxy.cglib_proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @version v1.0
 * @ClassName: ProxyFactory
 * @Description: 代理对象工厂，用来获取代理对象
 * @Author: 黑马程序员
 */
public class ProxyFactory1 implements MethodInterceptor {

    private Object factory ; //被代理对象的引用
    public Object getFactory(){
        return factory;
    }
    public void setFactory(Object factory){
        this.factory = factory;
    }

    public TrainStation getProxyObject() {
        //创建Enhancer对象，类似于JDK代理中的Proxy类
        Enhancer enhancer = new Enhancer();
        //设置父类的字节码对象。指定父类
        enhancer.setSuperclass(TrainStation.class);
        //设置回调函数
        enhancer.setCallback(this);
        //创建代理对象
        TrainStation proxyObject = (TrainStation) enhancer.create();
        return proxyObject;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代售点收取一定的服务费用(CGLib代理)");
        //要调用目标对象的方法
        Object obj = method.invoke(factory, objects);
        System.out.println("1");
        return obj;
    }
}
