package com.haoduor.graduation.adapter;

import com.haoduor.graduation.dto.TeacherDto;
import com.haoduor.graduation.model.Teacher;
import com.haoduor.graduation.vo.TeacherVo;

public class TeacherAdapter {
    private TeacherAdapter() {}

    public static TeacherDto teacherVoToDto(TeacherVo vo) {
        TeacherDto res = new TeacherDto();

        res.setId(vo.getTeacherId());
        res.setDepartment(vo.getDepartment());
        res.setName(vo.getName());

        return res;
    }
}
