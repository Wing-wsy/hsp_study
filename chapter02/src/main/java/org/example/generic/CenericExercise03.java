package org.example.generic;

/**
 * @author wing
 * @create 2023/12/20
 */
public class CenericExercise03 {
}
/* ================= 没有传默认Object ================= */
//class CC implements IUsb{} // 等价于 class CC implements IUsb<Object,Object>{}
/* ================= 实现接口时确定 ================= */
class BB implements IUsb<Integer,Float>{
    @Override
    public Float get(Integer integer) {return null;}
    @Override
    public void hi(Float aFloat) {}
    @Override
    public void run(Float r1, Float r2, Integer u1, Integer u2) {}
}
class AA implements IA{  // 当AA实现IUsb中的方法时，会自动将类型添加上
    @Override
    public Double get(String s) {return null;}
    @Override
    public void hi(Double aDouble) {}
    @Override
    public void run(Double r1, Double r2, String u1, String u2) {}
}
/* ================= 继承接口时确定 ================= */
interface IA extends IUsb<String,Double>{}
interface IUsb<U,R>{
    int n = 10;
    //U name;  接口的属性默认static，所以不能使用泛型
    // static void cry(R r){} 静态方法，不能使用泛型
    R get(U u);// 普通方法中(没有静态修饰)，可以使用接口泛型
    void hi(R r);
    void run(R r1,R r2,U u1,U u2);
    // 在JDK8中，可以在接口中，使用默认方法，也是可以使用泛型
    default R method(U u){ return null;}
}


