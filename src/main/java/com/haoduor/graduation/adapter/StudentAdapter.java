package com.haoduor.graduation.adapter;

import com.haoduor.graduation.model.Student;
import com.haoduor.graduation.vo.StudentVo;

public class StudentAdapter {

    private StudentAdapter(){}

    public static Student studentVoToStu(StudentVo vo) {
        Student stu = new Student();
        return stu;
    }
}
