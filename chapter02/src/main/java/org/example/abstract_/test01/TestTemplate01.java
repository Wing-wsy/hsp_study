package org.example.abstract_.test01;


/**
 * @author wing
 * @create 2023/12/13
 * 最初的代码方法
 */
public class TestTemplate01 {
    /**
     * AA 和 BB 都有一个计算的job方法
     * job 里面除了任务外，其他都是相同的代码
     * 弊端：如果来个CC，依然要写重复的代码
     */

    public static void main(String[] args) {
        AA aa = new AA();
        aa.job();

        BB bb = new BB();
        bb.job();
    }
}
