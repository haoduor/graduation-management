package com.haoduor.graduation.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@NotNull
public class TeacherVo {
    private long id;
    private String teacherId;
    private String name;
    private String department;
}
