package cn.itcast.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @author wing
 * @create 2024/11/7
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        /** 1）小白和厨师分开执行 */
//        println("小白进入餐厅");
//        println("小白点了番茄炒蛋 + 一碗米饭");
//        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
//            println("厨师炒菜");
//            thread(2000);
//            println("厨师打饭");
//            thread(1000);
//            return "番茄炒蛋 + 米饭 做好了";
//        });
//        println("小白在打王者");
//        println(cf1.join() + "小白开吃");

        /** 2）小白和厨师、服务员分开执行 */
//        println("小白进入餐厅");
//        println("小白点了番茄炒蛋 + 一碗米饭");
//        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
//            println("厨师炒菜");
//            thread(2000);
//            return "番茄炒蛋";
//        }).thenCompose(dish -> CompletableFuture.supplyAsync(() -> {
//            println("服务员打饭");
//            thread(1000);
//            return dish + " + 米饭";
//        }));
//        println("小白在打王者");
//        println(cf1.join() + "小白开吃");

        /** 3）小白和厨师、服务员分开执行（有所区别的是，厨师和服务员同时执行，但服务员还得等厨师执行完继续执行） */
        println("小白进入餐厅");
        println("小白点了番茄炒蛋 + 一碗米饭");
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            println("厨师炒菜");
            thread(5000);
            return "番茄炒蛋";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            println("服务员蒸饭");
            thread(3000);
            return "米饭";
        }),(dish,rice) -> {
            println("服务员打饭");
            thread(1000);
            return dish + rice + " + 好了";
        });
        println("小白在打王者");
        println(cf1.join() + "小白开吃");
    }

    private static void println(String content) {
        String threadName = "【" + Thread.currentThread().getName() + "】";
        String result = content + threadName;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = "【" + dateFormat.format(new Date()) + "】";
        System.out.println(date + result);
    }

    private static void thread(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
