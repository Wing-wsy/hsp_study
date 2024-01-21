package org.example.java8;

/**
 * @author wing
 * @create 2024/1/19
 */
public class FilterEmployeeByAge implements MyPredicate<Employee>{
    @Override
    public boolean test(Employee t) {
        return t.getAge() >= 35;
    }
}
