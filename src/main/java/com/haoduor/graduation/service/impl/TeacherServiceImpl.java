package com.haoduor.graduation.service.impl;

import com.haoduor.graduation.dao.TeacherMapper;
import com.haoduor.graduation.service.TeacherService;
import com.haoduor.graduation.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<TeacherVo> getTeacherVos() {
        return teacherMapper.selectTeacherVo();
    }
}
