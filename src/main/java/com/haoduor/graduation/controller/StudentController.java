package com.haoduor.graduation.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ResponseBody
    @GetMapping("/list")
    public PageMessage getStudent(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "30") int pageSize) {

        PageHelper.startPage(page, pageSize);
        List<StudentVo> res = studentService.getStudentVos();
        PageInfo<StudentVo> pages = new PageInfo<>(res);

        PageMessage pageMessage = new PageMessage();
        pageMessage.setTotalPage(pages.getPages());
        pageMessage.setNowPage(pages.getPageNum());
        pageMessage.setData(res);

        return pageMessage;
    }
}
