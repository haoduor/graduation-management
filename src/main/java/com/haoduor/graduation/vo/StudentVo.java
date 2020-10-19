package com.haoduor.graduation.vo;

import com.haoduor.graduation.model.Department;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@NotNull
public class StudentVo {
    long id;
    String studentId;
    String name;
    String classname;
    String department;
}
