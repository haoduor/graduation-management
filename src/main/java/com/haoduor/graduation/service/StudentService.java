package com.haoduor.graduation.service;

import com.haoduor.graduation.vo.StudentVo;

import java.util.List;

public interface StudentService {
    public List<StudentVo> getStudentVos();
    public boolean deleteStudentById(long id);
    public boolean updateStudent(StudentVo vo);
}
