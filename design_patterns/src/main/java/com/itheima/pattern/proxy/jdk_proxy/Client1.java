package com.itheima.pattern.proxy.jdk_proxy;

/**
 * @version v1.0
 * @ClassName: Client
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
public class Client1 {
    public static void main(String[] args) {

        //1,创建代理工厂对象
        ProxyFactory1 proxy = new ProxyFactory1();
        TrainStation station = new TrainStation();
        proxy.setFactory(station);
        //2,获取代理对象
        SellTickets sellTickets = (SellTickets)proxy.getProxyInstance();
        //3,调用代理方法
        sellTickets.sell();
    }
}
