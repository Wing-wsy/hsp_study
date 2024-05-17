package com.itheima.pattern.observable;

/**
 * @author wing
 * @create 2024/5/16
 */
public class Client {
    public static void main(String[] args) {
        //创建小偷对象
        Thief t = new Thief("隔壁老王");
        //创建警察对象
        Policemen p = new Policemen("小李");
        //让警察盯着小偷
        t.addObserver(p);
        //小偷偷东西xx
        t.steal();
    }
}
