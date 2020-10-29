package com.haoduor.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/manager")
    public String manager() {
        return "admin/index";
    }

    @GetMapping(value = {"", "/"})
    public String adminLoginPage() {
        return "";
    }

    @ResponseBody
    @PostMapping("/")
    public String adminLogin() {
        return null;
    }

}
