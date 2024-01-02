package org.example.generic.test;

import java.util.Comparator;

/**
 * @author wing
 * @create 2024/1/2
 */
public class ComparatorPerson implements Comparator<Person> {
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
}
