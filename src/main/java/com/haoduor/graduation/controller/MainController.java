package com.haoduor.graduation.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.haoduor.graduation.vo.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    public String login() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            // 根据用户角色分发页面
            if (currentUser.hasRole("student")) {
                return "redirect:/student";
            }

            if (currentUser.hasRole("teacher")) {
                return "redirect:/teacher";
            }

            if (currentUser.hasRole("admin")) {
                return "redirect:/admin/manager";
            }
        }

        return "login";
    }

    /**
     * 用户登录接口
     * @param username 用户名 学生 使用 学号  教师 使用 工号
     * @param password 密码
     * @param code 验证码
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseMessage login(@RequestParam String username,
                             @RequestParam String password) {
        Subject currentUser = SecurityUtils.getSubject();

        // 用户是否登录校验
        if (currentUser.isAuthenticated()) {
            return new BaseMessage(-1, "用户已登陆");
        }

        // 用户名与密码有效性校验
        if (!StrUtil.isEmpty(username) && !StrUtil.isEmpty(password)) {
            try {
                // shiro 用户登录
                currentUser.login(new UsernamePasswordToken(username, password));
                log.info("{} 用户成功登录", username);
                Session se = currentUser.getSession();
                se.setAttribute("username", username);

                return new BaseMessage(1, "登录成功");
            } catch (IncorrectCredentialsException e) {
                return new BaseMessage(5, "密码错误");
            } catch (UnknownAccountException e) {
                return new BaseMessage(6, "未知用户名");
            } catch (Exception e) {
                return new BaseMessage(7, "未知错误");
            }
        } else {
            return new BaseMessage(4, "用户名或者密码不能为空");
        }
    }

    // 登出接口
    @GetMapping("/logout")
    public String logout() {
        // 获取用户
        Subject currentUser = SecurityUtils.getSubject();
        // 用户登出
        currentUser.logout();
        // 重新向首页
        return "redirect:/";
    }

    @GetMapping("/student")
    public String studentPage() {
        // 学生的主页
        return "student/index";
    }

    @GetMapping("/teacher")
    public String teacherPage() {
        // 教师的主页
        return "teacher/index";
    }
}
