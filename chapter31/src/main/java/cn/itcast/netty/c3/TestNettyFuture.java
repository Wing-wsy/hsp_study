package cn.itcast.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wing
 * @create 2024/10/25
 */
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();

        // 2. 提交任务
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行计算：" + Thread.currentThread().getName());
                Thread.sleep(5000);
                return 80;
            }
        });

        System.out.println("处理别的事情。。。。");
        // 3. 主线程通过 future 来获取结果
        System.out.println("结果是：" + future.get() + "===" + Thread.currentThread().getName());
    }
}
