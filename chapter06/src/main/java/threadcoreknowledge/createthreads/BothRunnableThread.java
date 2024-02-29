package threadcoreknowledge.createthreads;

/**
 * 描述：     同时使用Runnable和Thread两种实现线程的方式。思路来自于网上的相同思路的面试题。
 */
public class BothRunnableThread {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我来自Runnable");
            }
        }) {
            @Override
            public void run() {
                System.out.println("我来自Thread");
            }
        }.start();
    }
}
