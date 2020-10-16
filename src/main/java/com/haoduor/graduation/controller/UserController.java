package com.haoduor.graduation.controller;

import cn.hutool.core.util.StrUtil;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.vo.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 管理员设置(更改)密码
    @ResponseBody
    @PostMapping("/pass")
    public BaseMessage renewPassword(@RequestParam long id, @RequestParam String password) {
        if (StrUtil.isEmpty(password)) {
            return new BaseMessage(2, "密码不能为空");
        }

        User u = userService.getUserById(id);
        if (u == null) {
            return new BaseMessage(3, "无效id");
        }

        if (userService.setUserPasswordById(id, password, u.getSalt())) {
            return new BaseMessage(1, "更改密码成功");
        } else {
            return new BaseMessage(4, "数据库错误");
        }
    }

    // 用户更改密码
    @ResponseBody
    @PostMapping("/repass")
    public BaseMessage resetPassword(String oldPassword, String password) {
        return null;
    }
}
