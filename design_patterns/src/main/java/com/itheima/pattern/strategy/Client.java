package com.itheima.pattern.strategy;

/**
 * @version v1.0
 * @ClassName: Client
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
public class Client {
    public static void main(String[] args) {
        //春节来了，使用春节促销活动
        SalesMan s = new SalesMan(new StrategyA());
        //展示促销活动
        s.salesManShow();

        System.out.println("==============");
        //中秋节到了，使用中秋节的促销活动
        s.setStrategy(new StrategyB());
        //展示促销活动
        s.salesManShow();

        System.out.println("==============");
        //圣诞节到了，使用圣诞节的促销活动
        s.setStrategy(new StrategyC());
        //展示促销活动
        s.salesManShow();
    }
}
