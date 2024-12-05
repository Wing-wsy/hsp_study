package org.example;

import org.example.dao.EmployeeRepository;
import org.example.pojo.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wing
 * @create 2024/12/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void add() {
        Employee employee = Employee.builder()
                .id("23").firstName("wang22").lastName("benson").empId(2).salary(12200).build();
        employeeRepository.save(employee);
    }

    @Test
    public void findAll() {
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(System.out::println);
    }

}
