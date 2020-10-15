package com.haoduor.graduation.controller;

import com.haoduor.graduation.vo.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/upload")
    public BaseMessage upload(MultipartFile file) {
        return null;
    }

    @GetMapping("/download/{sha256}")
    public BaseMessage download(@PathVariable String sha256) {
        return null;
    }

}
