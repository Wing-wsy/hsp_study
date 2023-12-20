package org.example.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2023/12/20
 */
public class CenericExercise05 {
    public static void main(String[] args) {
        List<Object> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<AAA> list3 = new ArrayList<>();
        List<BBB> list4 = new ArrayList<>();
        List<CCC> list5 = new ArrayList<>();
        // <?> : 可以接收所有泛型类型
        printCollection1(list1);
        printCollection1(list2);
        printCollection1(list3);
        printCollection1(list4);
        printCollection1(list5);
        // <? extends AAA> : 可以接收AAA或者AAA的子类
        printCollection2(list3);
        printCollection2(list4);
        printCollection2(list5);
        // <? super AAA> : 可以接收AAA或者AAA的父类，不限于直接父类
        printCollection3(list1);
        printCollection3(list3);
    }
    // List<?> 表示任意的泛型类型都可以接收
    public static void printCollection1(List<?> c){
        for(Object object : c){ // 通配符，取出来时就是Object
            System.out.println(object);
        }
    }
    // List<? extends AAA> 表示上限，可以接收AAA或者AAA的子类
    public static void printCollection2(List<? extends AAA> c){
    }
    // List<? super AAA> 表示下限，可以接收AAA或者AAA的父类，不限于直接父类
    public static void printCollection3(List<? super AAA> c){
    }
}

class AAA{
}
class BBB extends AAA{
}
class CCC extends BBB{
}