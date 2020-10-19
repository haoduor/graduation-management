package com.haoduor.graduation.adapter;

import com.haoduor.graduation.dto.StudentDto;
import com.haoduor.graduation.model.Student;
import com.haoduor.graduation.vo.StudentVo;

public class StudentAdapter {

    private StudentAdapter(){}

    public static StudentDto studentVoToDto(StudentVo vo) throws NumberFormatException {
        StudentDto res = new StudentDto();

        res.setId(Long.parseLong(vo.getStudentId()));

        res.setName(vo.getName());
        res.setDepartment(vo.getDepartment());
        res.setClassname(vo.getClassname());

        return res;
    }
}
