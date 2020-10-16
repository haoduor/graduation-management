package com.haoduor.graduation.vo;

import com.haoduor.graduation.model.Department;
import lombok.Data;

@Data
public class StudentVo {
    String id;
    String name;
    String classname;
    Department department;
}
