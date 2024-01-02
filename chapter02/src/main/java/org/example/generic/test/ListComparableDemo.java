package org.example.generic.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author wing
 * @create 2024/1/2
 */
public class ListComparableDemo {

    public static void main(String[] args) {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("小A",35));
        persons.add(new Person("小C",25));
        persons.add(new Person("小D",25));
        persons.add(new Person("小B",25));
        // 排序
        ComparatorPerson comparatorPerson = new ComparatorPerson();//比较器
        // 方式1
        // Collections.sort(persons,comparatorPerson);//排序
        // 方式2:匿名内部类
        Collections.sort(persons,new Comparator<Person>(){
            @Override
            public int compare(Person o1, Person o2) {
                // 先按年龄升序排序
                int ageComparison = o1.getAge() - o2.getAge();
                if (ageComparison != 0) {
                    return ageComparison;
                }
                // 如果年龄相等，则按姓名字母顺序排序
                return o1.getName().compareTo(o2.getName());
            }
        });
        // 循环
        for (Person person : persons) {
            System.out.println(person);
        }
    }
}
