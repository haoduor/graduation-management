package com.haoduor.graduation.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/personal")
    public Object getPersonalInfo() {
        Subject sub = SecurityUtils.getSubject();
        return sub.getPrincipal();
    }
}
