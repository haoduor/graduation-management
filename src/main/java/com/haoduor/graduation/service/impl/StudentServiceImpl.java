package com.haoduor.graduation.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import com.haoduor.graduation.dao.StudentMapper;
import com.haoduor.graduation.dao.UserMapper;
import com.haoduor.graduation.model.Department;
import com.haoduor.graduation.model.Student;
import com.haoduor.graduation.model.StudentExample;
import com.haoduor.graduation.model.UserExample;
import com.haoduor.graduation.service.DepartmentService;
import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserService userService;

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
    public boolean updateStudentByVo(StudentVo vo) {
        StudentExample se = new StudentExample();
        se.createCriteria().andUserIdEqualTo(vo.getId());

        Student s = new Student();
        s.setUserId(vo.getId());
        s.setName(vo.getName());
        s.setClassName(vo.getClassname());

        Department d = departmentService.getOrAddDepartment(vo.getDepartment());

        if (d != null) {
            s.setDepartmentId(d.getId());

            int res = studentMapper.updateByExample(s, se);
            return res == 1;
        }

        return false;
    }


    @Override
    public Student getStudentById(long id) {
        StudentExample se = new StudentExample();
        se.createCriteria().andUserIdEqualTo(id);
        List<Student> res = studentMapper.selectByExample(se);
        return CollectionUtil.getFirst(res);
    }

    @Override
    public boolean hasStudent(long id) {
        return getStudentById(id) == null;
    }
}
