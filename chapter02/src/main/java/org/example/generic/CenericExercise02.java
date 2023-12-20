package org.example.generic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author wing
 * @create 2023/12/19
 */
public class CenericExercise02 {

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("tom",20000,new MyDate(2000,11,12)));
        employees.add(new Employee("jack",12000,new MyDate(2001,11,12)));
        employees.add(new Employee("tom",20000,new MyDate(1900,10,12)));
        System.out.println(employees);
        System.out.println("=========排序后=========");

        // 开始对员工进行指定方式排序
        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                //先按照name排序，如果name相同，则按生日日期的先后排序。【即：定制排序】
                //先对传入的参数进行验证
                if(!(emp1 instanceof Employee && emp2 instanceof Employee)){
                    System.out.println("类型不正确");
                    return 0;
                }
                // 比较name
                int i = emp1.getName().compareTo(emp2.getName());
                if(i != 0){
                    return i;
                }
                /* 这一段代码放到MyDate中实现
                //如果name相同，就比较birthdy - year
                int yearMinus = emp1.getBirthday().getYear() - emp2.getBirthday().getYear();
                if(yearMinus != 0){
                    return yearMinus;
                }
                //如果year相同，就比较month
                int monthMinus = emp1.getBirthday().getMonth() - emp2.getBirthday().getMonth();
                if(monthMinus != 0){
                    return monthMinus;
                }
                return emp1.getBirthday().getDay() - emp2.getBirthday().getDay(); */

                //注释上面的之后，这里只需要写这一行就行
                return emp1.getBirthday().compareTo(emp2.getBirthday());
            }
        });
        System.out.println(employees);

    }
}
