package top.jacktgq;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 入门体验
 */
public class TimerTaskApp {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer task run...");
            }
        };
        // 每隔两秒执行run方法
        timer.schedule(task, 0, 2000);
    }
}
