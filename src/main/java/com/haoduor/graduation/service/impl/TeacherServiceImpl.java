package com.haoduor.graduation.service.impl;

import com.haoduor.graduation.dao.TeacherMapper;
import com.haoduor.graduation.dao.UserMapper;
import com.haoduor.graduation.model.Department;
import com.haoduor.graduation.model.Teacher;
import com.haoduor.graduation.model.TeacherExample;
import com.haoduor.graduation.model.UserExample;
import com.haoduor.graduation.service.DepartmentService;
import com.haoduor.graduation.service.TeacherService;
import com.haoduor.graduation.vo.TeacherVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<TeacherVo> getTeacherVos() {
        return teacherMapper.selectTeacherVo();
    }

    @Override
    public boolean deleteTeacherById(long id) {
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andUserIdEqualTo(id);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);

        int resT = teacherMapper.deleteByExample(teacherExample);
        int resU = userMapper.deleteByExample(userExample);
        return resT == 1 && resU == 1;
    }

    @Override
    public boolean updateTeacherByVo(TeacherVo vo) {
        Teacher t = new Teacher();

        t.setUserId(vo.getId());
        t.setName(vo.getName());
        Department de = departmentService.getOrAddDepartment(vo.getDepartment());
        t.setDepartmentId(de.getId());

        TeacherExample te = new TeacherExample();
        te.createCriteria().andUserIdEqualTo(vo.getId());

        int res = teacherMapper.updateByExample(t, te);
        return res == 1;
    }
}
