package com.haoduor.graduation.controller;

import com.haoduor.graduation.vo.BaseMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    // 管理员设置密码
    @ResponseBody
    @PostMapping("/pass")
    public BaseMessage renewPassword(String password) {
        return null;
    }

    // 用户更改密码
    @ResponseBody
    @PostMapping("/repass")
    public BaseMessage resetPassword(String oldPassword, String password) {
        return null;
    }
}
