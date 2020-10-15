package com.haoduor.graduation.init;

import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.RoleMapper;
import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.RoleExample;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public void run(String... args) throws Exception {
        if (!roleIsGenerate()) {
            List<Role> roles = getAllRoles();

            for (Role r : roles) {
                roleMapper.insert(r);
            }
        }


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
