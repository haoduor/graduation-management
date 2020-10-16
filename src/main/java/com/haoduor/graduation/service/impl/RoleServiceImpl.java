package com.haoduor.graduation.service.impl;


import com.haoduor.graduation.dao.RoleMapper;
import com.haoduor.graduation.dao.UserMapper;
import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.RoleExample;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    private static RoleExample createRoleExampleWithNameE(String name) {
        RoleExample re = new RoleExample();
        re.createCriteria().andNameEqualTo(name);

        return re;
    }


    @Override
    public Role getRoleById(long id) {
        RoleExample re = new RoleExample();
        re.createCriteria().andIdEqualTo(id);

        List<Role> roles = roleMapper.selectByExample(re);

        if (roles.size() == 1) {
            return roles.get(0);
        }

        return null;
    }

    @Override
    public Role getRoleByName(String name) {
        List<Role> roles = roleMapper.selectByExample(createRoleExampleWithNameE(name));

        if (roles.size() == 1) {
            return roles.get(0);
        }
        return null;
    }

    @Override
    public Role getAdminRole() {
        return getRoleByName("admin");
    }

    @Override
    public Role getStudentRole() {
        return getRoleByName("student");
    }

    @Override
    public Role getTeacherRole() {
        return getRoleByName("teacher");
    }
}
