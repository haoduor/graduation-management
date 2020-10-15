package com.haoduor.graduation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/import")
public class UploadController {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;




}
