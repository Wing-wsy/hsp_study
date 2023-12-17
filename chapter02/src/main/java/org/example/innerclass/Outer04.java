package org.example.innerclass;

/**
 * @author wing
 * @create 2023/12/16
 */
public class Outer04 {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner r = outer.new Inner();
        System.out.println(r.a);
    }
}

class Outer{
    public Outer(){
        Inner s1 = new Inner();
        s1.a = 10;
        Inner s2 = new Inner();
        System.out.println(s2.a);
    }

    class Inner{
        public int a = 5;
    }
}
