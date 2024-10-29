package cn.itcast.netty.c3;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author wing
 * @create 2024/10/24
 */
public class TestEventLoop {
    public static void main(String[] args) {
        // 1. 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(2); // io 事件，普通任务，定时任务 都能处理
//        EventLoopGroup group = new DefaultEventLoopGroup(); // 只能处理 普通任务，定时任务
        // 2. 获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        // 3. 执行普通任务
//        group.next().submit(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("ok:" + Thread.currentThread());
//        });

        // 4. 执行定时任务
        group.next().scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ok:" + Thread.currentThread());
        }, 0 ,1, TimeUnit.SECONDS);
        System.out.println("main" + Thread.currentThread());
    }
}
