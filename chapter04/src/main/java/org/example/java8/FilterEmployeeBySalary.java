package org.example.java8;

/**
 * @author wing
 * @create 2024/1/19
 */
public class FilterEmployeeBySalary implements MyPredicate<Employee>{
    @Override
    public boolean test(Employee t) {
        return t.getSalary() >= 5000;
    }
}
