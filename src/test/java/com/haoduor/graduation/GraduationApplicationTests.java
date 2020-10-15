package com.haoduor.graduation;

import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.StudentMapper;
import com.haoduor.graduation.dao.TeacherMapper;
import com.haoduor.graduation.dao.UserMapper;
import com.haoduor.graduation.model.*;
import com.haoduor.graduation.service.RoleService;
import com.haoduor.graduation.util.EncryptedUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GraduationApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Snowflake snowflake;

    @Test
    public void contextLoads() {}

    /**
     * 生成测试学生
     * 学生学号作为用户名
     * 密码 qwe123
     */
    @Test
    public void generateTestStudent() {
        User u = new User();
        Student stu = new Student();

        String salt = EncryptedUtil.getSalt();
        String password = "qwe123";
        Role student = roleService.getStudentRole();

        u.setId(snowflake.nextId());
        u.setUsername("165532133");
        u.setPassword(EncryptedUtil.encryptedPassword(password, salt));
        u.setSalt(salt);
        u.setRoleId(student.getId());

        stu.setUserId(u.getId());
        stu.setClassName("17软件");
        stu.setName("张三");

        userMapper.insert(u);
        studentMapper.insert(stu);
    }

    /**
     * 生成测试教师
     * 教师工号作为用户名
     * 密码 qwe123
     */
    @Test
    public void generateTestTeacher() {
        User u = new User();
        Teacher tea = new Teacher();

        String salt = EncryptedUtil.getSalt();
        String password = "qwe123";
        Role teacher = roleService.getTeacherRole();

        u.setId(snowflake.nextId());
        u.setUsername("1655337");
        u.setPassword(EncryptedUtil.encryptedPassword(password, salt));
        u.setSalt(salt);
        u.setRoleId(teacher.getId());

        tea.setUserId(u.getId());
        tea.setName("雷雨");

        userMapper.insert(u);
        teacherMapper.insert(tea);
    }

    /**
     * 删除测试数据
     */
    @Test
    public void deleteData() {
        userMapper.deleteByExample(new UserExample());
        teacherMapper.deleteByExample(new TeacherExample());
        studentMapper.deleteByExample(new StudentExample());
    }
}
