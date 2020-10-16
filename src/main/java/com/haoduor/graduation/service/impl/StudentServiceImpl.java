package com.haoduor.graduation.service.impl;

import com.haoduor.graduation.dao.StudentMapper;
import com.haoduor.graduation.dao.UserMapper;
import com.haoduor.graduation.model.Student;
import com.haoduor.graduation.model.StudentExample;
import com.haoduor.graduation.model.UserExample;
import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<StudentVo> getStudentVos() {
        return studentMapper.selectStudentVo();
    }

    @Override
    public boolean deleteStudentById(long id) {
        UserExample ue = new UserExample();
        ue.createCriteria().andIdEqualTo(id);

        StudentExample se = new StudentExample();
        se.createCriteria().andUserIdEqualTo(id);

        int userRes = userMapper.deleteByExample(ue);
        int studentRes = studentMapper.deleteByExample(se);
        return userRes == 1 && studentRes == 1;
    }

    @Override
    public boolean updateStudent() {
        return false;
    }
}
