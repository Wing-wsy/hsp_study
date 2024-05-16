package com.itheima.pattern.proxy.cglib_proxy;//package com.itheima.pattern.proxy.cglib_proxy;

/**
 * @version v1.0
 * @ClassName: Client
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
public class Client1 {
    public static void main(String[] args) {
        //创建代理工厂对象
        ProxyFactory1 factory = new ProxyFactory1();
        TrainStation trainStation = new TrainStation();
        factory.setFactory(trainStation);
        //获取代理对象
        TrainStation proxyObject = factory.getProxyObject();
        //调用代理对象中的sell方法卖票
        proxyObject.sell();


    }
}
