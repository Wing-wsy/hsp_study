package org.example.chapter05;

/**
 * @author wing
 * @create 2024/2/2
 * invokestatic指令和invokespecial指令调用的方法称为非虛方法
 */

class Father {
    public Father() {
        System.out.println("father的构造器");
    }

    public static void showStatic(String str) {
        System.out.println("father " + str);
    }

    public final void showFinal() {
        System.out.println("father show final");
    }

    public void showCommon() {
        System.out.println("father 普通方法");
    }
}
public class Son extends Father{
    public Son() {
        // invokespecial
        super();
    }
    public Son(int age) {
        // invokespecial
        this();
    }
//    public static void showStatic(String str) {
//        System.out.println("son " + str);
//    }
    private void showPrivate(String str) {
        System.out.println("son private " + str);
    }

    public void info() {

    }
    public void show() {
        // invokestatic
        showStatic("Wing");
        // invokestatic
//        super.showStatic("good!");
        // invokespecial
        showPrivate("hello!");
        // invokespecial
        super.showCommon();
        // invokevirtual
        showFinal();  // 因为此方法声明有final，不能被子类重写，所以也认为此方法是非虚方法
        // invokevirtual 编译时确定不下来，因为子类可能重写该方法
        showCommon();
        info();

        MethodInterface in = null;
        // invokeinterface
        in.methodA();
    }

    public static void main(String[] args) {
        Son so = new Son();
        so.show();
    }
}

interface MethodInterface {
    void methodA();
}


