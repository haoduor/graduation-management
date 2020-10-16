package com.haoduor.graduation.service.impl;

import com.haoduor.graduation.dao.StudentMapper;
import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<StudentVo> getStudentVos() {
        return studentMapper.selectStudentVo();
    }
}
