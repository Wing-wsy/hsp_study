package org.example.dao;

import org.example.pojo.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author wing
 * @create 2024/12/4
 */
public interface EmployeeRepository extends MongoRepository<Employee,String> {}
