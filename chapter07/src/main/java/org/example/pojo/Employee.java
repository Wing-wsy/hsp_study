package org.example.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("employee")
public class Employee implements Serializable {
    @Id
    private String id;
    private int empId;
    private String firstName;
    private String lastName;
    private float salary;
}