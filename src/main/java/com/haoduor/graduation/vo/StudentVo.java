package com.haoduor.graduation.vo;

import com.haoduor.graduation.model.Department;
import lombok.Data;

@Data
public class StudentVo {
    long id;
    String studentId;
    String name;
    String classname;
    String department;
}
