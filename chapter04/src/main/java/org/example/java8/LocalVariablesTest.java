package org.example.java8;

import java.util.Date;

/**
 * @author wing
 * @create 2024/1/29
 */
public class LocalVariablesTest {
    int count = 1;

    public LocalVariablesTest(){
        this.count = 1;
    }

    public void test1() {
        Date date = new Date();
        String name1 = "Wing";
        String info = test2(date, name1);
        System.out.println(date + name1);

    }
    public String test2(Date dateP, String name2) {
        dateP = null;
        name2 = "Li";
        double weight = 130.5;
        char gender = '男';
        return dateP + name2;
    }
        public static void testStatic() {
        LocalVariablesTest test = new LocalVariablesTest();
        Date date = new Date();
        int count = 10;
        System.out.println(count);
    }

    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        // 变量c使用之前已经销毁的变量b占据的slot的位置
        int c = a + 1;
    }

    public void testAddOperation() {
        byte i = 15;
        int j = 8;
        int k = i + j;
    }

    public int getSum() {
        int m = 10;
        int n = 20;
        int k = m + n;
        return k;
    }

    public void testGetSum() {
        int i = getSum();
        int j = 10;
    }
}
