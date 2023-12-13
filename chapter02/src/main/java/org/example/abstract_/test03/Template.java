package org.example.abstract_.test03;

/**
 * @author wing
 * @create 2023/12/13
 * 改进最终版 抽象类-模板设计模式
 */
abstract public class Template {
    public abstract void job();// 抽象方法

    public void calculateTime(){
        long start = System.currentTimeMillis();
        job();
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start));
    }
}
