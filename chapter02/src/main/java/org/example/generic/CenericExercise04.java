package org.example.generic;

import java.util.ArrayList;

/**
 * @author wing
 * @create 2023/12/20
 */
public class CenericExercise04 {
    public static void main(String[] args) {
        Car car = new Car();
        car.fly("宝马",100); // 当调用方法时传入参数，编译器就会确定类型
        System.out.println("==========");
        Car car2 = new Car();
        car2.fly(300,1.1);
        System.out.println("==========");
        Fish<String, ArrayList> fish = new Fish<>();
        fish.hello(new ArrayList(),11.3f);
    }
}

/* 普通类中使用泛型方法 */
class Car{ //普通类
    public void run(){ //普通方法
    }
    //说明：
    //1.<T,R>就是泛型
    //2.提供给 fly 使用的
    public <T,R> void fly(T t,R r){ // 泛型方法
        System.out.println(t.getClass()); //class java.lang.String
        System.out.println(r.getClass()); //class java.lang.Integer
    }
}
/* 泛型类中使用泛型方法 */
class Fish<T,R>{
    public void run(){ //普通方法
    }
    public <U,M> void eat(U u,M m){ // 泛型方法
    }
    //说明：
    //1.下面hi方法不是泛型方法
    //2.是hi方法使用了类声明的泛型
    public void hi(T t){}
    //泛型方法，可以使用类声明的泛型，也可以使用自己声明的泛型
    public<K> void hello(R r,K k){
        System.out.println(r.getClass());
        System.out.println(k.getClass());
    }
}