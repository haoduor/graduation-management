package com.haoduor.graduation.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // 管理员页面
    @GetMapping("/manager")
    @RequiresRoles("admin")
    public String manager() {
        return "admin/index";
    }

}
