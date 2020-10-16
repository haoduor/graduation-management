package com.haoduor.graduation.init;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.RoleMapper;
import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.RoleExample;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.RoleService;
import com.haoduor.graduation.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("snowflake")
    private Snowflake snowflake;

    @Resource(name = "studentFilter")
    private BitMapBloomFilter studentFilter;

    @Resource(name = "teacherFilter")
    private BitMapBloomFilter teacherFilter;

    // 服务器初始化
    @Override
    public void run(String... args) throws Exception {
        if (!roleIsGenerate()) {
            log.info("首次初始化 角色库");
            List<Role> roles = getAllRoles();

            for (Role r : roles) {
                roleMapper.insert(r);
            }
        }

        Role student = roleService.getStudentRole();
        Role teacher = roleService.getTeacherRole();

        log.info("初始化用户库");
        List<User> students = userService.getUserByRole(student.getId());
        students.forEach(s -> studentFilter.add(s.getUsername()));

        log.info("初始化教师库");
        List<User> teachers = userService.getUserByRole(teacher.getId());
        teachers.forEach(s -> teacherFilter.add(s.getUsername()));
    }

    private List<Role> getAllRoles() {
        List<Role> roles = new LinkedList<>();

        Role admin = new Role();
        Role student = new Role();
        Role teacher = new Role();

        admin.setId(snowflake.nextId());
        student.setId(snowflake.nextId());
        teacher.setId(snowflake.nextId());

        admin.setName("admin");
        student.setName("student");
        teacher.setName("teacher");

        roles.add(admin);
        roles.add(student);
        roles.add(teacher);
        return roles;
    }

    private boolean roleIsGenerate() {
        List<Role> res = roleMapper.selectByExample(new RoleExample());

        return res.size() == 3;
    }

}
