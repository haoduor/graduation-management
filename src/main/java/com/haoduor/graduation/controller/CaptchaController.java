package com.haoduor.graduation.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.io.IoUtil;
import com.haoduor.graduation.util.LoginCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {
    @Autowired
    private HttpServletRequest request;

    // 获取验证码接口
    @GetMapping("/get")
    public void getCaptcha(HttpServletResponse response) {
        // 创建验证码对象
        LineCaptcha lineCaptcha = new LineCaptcha(90, 40 , 4, 50);

        // 设置验证码生成规则
        lineCaptcha.setGenerator(new LoginCodeGenerator());

        // 将生成的验证码存储至用户session中
        HttpSession s = request.getSession();
        s.setAttribute("code", lineCaptcha.getCode());

        // 输出验证码图像至其前台
        try {
            OutputStream os = response.getOutputStream();
            lineCaptcha.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
