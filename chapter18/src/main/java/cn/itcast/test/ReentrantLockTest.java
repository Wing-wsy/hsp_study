package cn.itcast.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wing
 * @create 2024/11/7
 */
public class ReentrantLockTest {

    private ReentrantLock lock = new ReentrantLock();

    public void test() {
        lock.lock();
        try {
            System.out.println("业务处理。。。");
        } finally {
            lock.unlock();
        }
    }

}
