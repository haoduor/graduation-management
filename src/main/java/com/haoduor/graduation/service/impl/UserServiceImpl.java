package com.haoduor.graduation.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.StudentMapper;
import com.haoduor.graduation.dao.TeacherMapper;
import com.haoduor.graduation.dao.UserMapper;
import com.haoduor.graduation.dto.StudentDto;
import com.haoduor.graduation.dto.TeacherDto;
import com.haoduor.graduation.model.*;
import com.haoduor.graduation.service.DepartmentService;
import com.haoduor.graduation.service.RoleService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.util.EncryptedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private RoleService roleService;

    @Override
    public User getUserByName(String username) {
        UserExample ue = new UserExample();
        ue.createCriteria().andUsernameEqualTo(username);

        List<User> users = userMapper.selectByExample(ue);

        if (users.size() == 1) {
            return users.get(0);
        }

        return null;
    }

    @Override
    public User getUserById(long id) {
        UserExample ue = new UserExample();
        ue.createCriteria().andIdEqualTo(id);

        List<User> res = userMapper.selectByExample(ue);

        if (res.size() == 1) {
            return res.get(0);
        }

        return null;
    }

    @Override
    public List<User> getUserByRole(long roleId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andRoleIdEqualTo(roleId);

        return userMapper.selectByExample(userExample);
    }

    @Override
    public List<User> getAllUsers() {
        UserExample ue = new UserExample();

        return userMapper.selectByExample(ue);
    }

    @Override
    public boolean setUserPasswordByUser(long id, User user) {
        UserExample ue = new UserExample();
        ue.createCriteria().andIdEqualTo(id);

        int res = userMapper.updateByExampleSelective(user, ue);

        return res == 1;
    }

    @Override
    public boolean setUserPasswordById(long id, String password, String salt) {
        UserExample ue = new UserExample();
        ue.createCriteria().andIdEqualTo(id);

        User u = new User();
        u.setPassword(EncryptedUtil.encryptedPassword(password, salt));
        int res = userMapper.updateByExampleSelective(u, ue);

        return res == 1;
    }

    @Override
    public boolean addStudentDto(StudentDto s) {
        Role sr = roleService.getStudentRole();

        User user = new User();
        user.setId(snowflake.nextId());
        user.setUsername(String.valueOf(s.getId()));
        user.setSalt(EncryptedUtil.getSalt());
        user.setPassword(EncryptedUtil.encryptedPassword("123456", user.getSalt()));
        user.setRoleId(sr.getId());

        Student stu = new Student();
        stu.setUserId(user.getId());
        stu.setName(s.getName());
        stu.setClassName(s.getClassname());
        Department de = departmentService.getOrAddDepartment(s.getDepartment());
        stu.setDepartmentId(de.getId());

        int resU = userMapper.insert(user);
        int resS = studentMapper.insert(stu);

        return resU == 1 && resS == 1;
    }

    @Override
    public boolean addTeacherDto(TeacherDto t) {
        Role teacher = roleService.getTeacherRole();

        User u = new User();
        u.setId(snowflake.nextId());
        u.setUsername(t.getId());
        u.setRoleId(teacher.getId());
        u.setSalt(EncryptedUtil.getSalt());
        u.setPassword(EncryptedUtil.encryptedPassword("123456", u.getSalt()));

        Teacher te = new Teacher();
        te.setUserId(u.getId());
        te.setName(t.getName());

        Department de = departmentService.getOrAddDepartment(t.getDepartment());
        te.setDepartmentId(de.getId());

        int resU = userMapper.insert(u);
        int resT = teacherMapper.insert(te);

        return resT == 1 && resU == 1;
    }

    @Override
    public boolean hasAdmin() {
        UserExample ue = new UserExample();
        Role admin = roleService.getAdminRole();
        ue.createCriteria().andRoleIdEqualTo(admin.getId());

        int sum = userMapper.countByExample(ue);

        return sum > 0;
    }

    @Override
    public boolean addUser(User u) {
        int res = userMapper.insert(u);

        return res == 1;
    }
}
