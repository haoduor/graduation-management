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
}
