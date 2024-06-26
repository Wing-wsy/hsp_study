package com.qfedu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    private String stuId;//学号
    private String stuName;
    private int stuAge;
    private List<Course> courses;//班级
}

