package com.haoduor.graduation.controller;

import cn.hutool.core.util.StrUtil;
import com.haoduor.graduation.vo.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String login() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseMessage login(@RequestParam String username, @RequestParam String password, @RequestParam String code) {
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            return new BaseMessage(-1, "用户已登陆");
        }

        if (StrUtil.isEmpty(code)) {
            return new BaseMessage(2, "验证码不能为空");
        }

        String inCode = (String) currentUser.getSession().getAttribute("code");

        if (inCode.equals(code)) {
            if (!StrUtil.isEmpty(username) && !StrUtil.isEmpty(password)) {
                try {
                    currentUser.login(new UsernamePasswordToken(username, password));
                    log.info("{} 用户成功登录", username);
                    return new BaseMessage(1, "登录成功");
                } catch (IncorrectCredentialsException e) {
                    return new BaseMessage(5, "密码错误");
                } catch (UnknownAccountException e) {
                    return new BaseMessage(6, "未知用户名");
                }
            } else {
                return new BaseMessage(4, "用户名或者密码不能为空");
            }
        } else {
            return new BaseMessage(3, "验证码错误");
        }
    }
}
