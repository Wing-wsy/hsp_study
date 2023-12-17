package org.example.interface_;

/**
 * @author wing
 * @create 2023/12/14
 */
public class test {

    public static void main(String[] args) {
        Outer02 outer02 = new Outer02();
        outer02.m1();
        System.out.println("outer02 hashCode:" + outer02.hashCode());
    }
}

class Outer02{  // 外部类
    private int n1 = 100;
    private void m2(){  // 私有方法
        System.out.println("Outer02 m2()");
    }
    public void m1(){  // 公有方法
        //1.局部内部类是定义在外部类的局部位置，通常在方法
        //3.不能添加访问修饰符，但是可以使用final 修饰
        //4.作用域：仅仅在定义它的方法或代码块中
        final class Inner02{//局部内部类(本质仍然是一个类)
            private int n1 = 200;
            //2.可以直接访问外部类的所有成员，包含私有的,见f1方法
            public void f1(){
                //5．局部内部类可以直接访问外部类的成员，比如下面 外部类n1 和m2()
                // 6如果外部类和局部内部类的成员重名时都是n1，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名.this.成员）去访问
                System.out.println("n1=" + n1 + "  " + Outer02.this.n1 + "  " + this.n1 );
                System.out.println("Outer02.this hashCode:" + Outer02.this.hashCode());
                System.out.println("this hashCode:" + this.hashCode());
                m2();
            }
        }
        Inner02 inner02 = new Inner02();
        inner02.f1();
        System.out.println("inner02 hashCode:" + inner02.hashCode());
    }
}


