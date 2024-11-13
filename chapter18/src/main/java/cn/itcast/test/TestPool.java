package cn.itcast.test;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wing
 * @create 2024/6/15
 */
public class TestPool {
//    public static void main(String[] args) {
//        // 参数1:核心线程数
//        // 参数2:获取任务的超时时间
//        // 参数3:时间单位
//        // 参数4:队列长度
////        Executors.newFixedThreadPool()
////        ThreadPoolExecutor executor = new ThreadPoolExecutor();
//        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 1, (queue, task) -> {
//            // 1) 死等
//            //queue.put(task);
//            // 2) 带超时等待
//            queue.offer(task, 500, TimeUnit.MILLISECONDS);
//            // 3) 让调用者放弃任务执行（什么都不写代表放弃）
//            System.out.println("放弃");
//            // 4) 让调用者抛出异常
//            throw new RuntimeException("任务执行失败");
//            // 5) 让调用者自己执行任务
////            task.run();
//        });
//        for (int i = 0; i < 3; i++) {
//            int j = i;
//            threadPool.execute(() -> {
//                try {
//                    // 模拟每个任务执行时间特别长
//                    Thread.sleep(1000000L);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println("j=====================任务 " + j);
//            });
//        }
//    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Executors.newSingleThreadExecutor();
//        Future<String> future = pool.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                System.out.println("call。。。");
//                Thread.sleep(1000);
//                return "ok";
//            }
//        });
//        System.out.println(future.get());

//        Future<String> future = pool.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("run。。。");
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, "okk" );
//        System.out.println(future.get());
//        System.out.println("main...");

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        System.out.println("111" + new Date());
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello..." + new Date());
            }
        },3,TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("nihao..." + new Date());
            }
        },5,3,TimeUnit.SECONDS);



    }
}

