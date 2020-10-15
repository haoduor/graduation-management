package com.haoduor.graduation.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.UserMapper;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.model.UserExample;
import com.haoduor.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Snowflake snowflake;

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
    public List<User> getAllUsers() {
        UserExample ue = new UserExample();

        return userMapper.selectByExample(ue);
    }
}
