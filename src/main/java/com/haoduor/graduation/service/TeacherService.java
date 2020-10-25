package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Student;
import com.haoduor.graduation.model.Teacher;
import com.haoduor.graduation.vo.TeacherVo;

import java.util.List;

public interface TeacherService {
    public List<TeacherVo> getTeacherVos();
    public boolean deleteTeacherById(long id);
    public boolean updateTeacherByVo(TeacherVo vo);
    public Teacher getTeacherById(long id);
}
