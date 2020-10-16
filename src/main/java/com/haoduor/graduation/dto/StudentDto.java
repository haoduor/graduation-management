package com.haoduor.graduation.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudentDto {
    long id;
    String name;
    String classname;
    String department;
}
