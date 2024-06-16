package cn.itcast.test;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @author wing
 * @create 2024/6/15
 */
public class ThreadPool {
    // 任务队列
    private BlockingQueue<Runnable> taskQueye;

    // 线程集合
    private HashSet<Workder> workders = new HashSet<Workder>();

    // 核心线程数
    private int coreSize;

    // 获取任务的超时时间
    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapcity, RejectPolicy rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueye = new BlockingQueue<>(queueCapcity);
        this.rejectPolicy = rejectPolicy;
    }

    // 执行任务
    public void execute(Runnable task) {
        // 当任务数没有超过 coreSize 时，直接交给 workder 对象执行
        // 如果任务数超过 coreSize 时，加入任务队列暂存
        synchronized (workders) {
            if (workders.size() < coreSize) {
                Workder workder = new Workder(task);
                System.out.println("新增 worker" + workder + "," + task);
                workders.add(workder);
                workder.start();
            } else {
                // 有拒绝策略
                taskQueye.tryPut(rejectPolicy, task);
                // 无拒绝策略
                //taskQueye.put(task);
                // 如果队列满了，使用不同的策略，使用策略模式，让调用者传进来策略
                // 1) 死等
                // 2) 带超时等待
                // 3) 让调用者放弃任务执行
                // 4) 让调用者抛出异常
                // 5) 让调用者自己执行任务
            }
        }
    }

    class Workder extends Thread{
        private Runnable task;

        public Workder(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // 1） 当task 不为空，执行任务
            // 2） 当task 执行完毕，再接着从任务队列获取任务并执行
            // 没有超时时间
//            while (task != null || (task = taskQueye.take()) != null) {
            // 有超时时间,内部类可以直接使用外部类成员变量
            while (task != null || (task = taskQueye.poll(timeout, timeUnit)) != null) {
                try {
                    System.out.println("正在执行... " + task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }

            // 移除
            synchronized (workders) {
                System.out.println("workder 被移除 " + this);
                workders.remove(this);
            }
        }
    }
}

@FunctionalInterface // 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

