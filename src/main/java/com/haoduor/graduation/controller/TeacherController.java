package com.haoduor.graduation.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.service.TeacherService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    public PageMessage getTeacher(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "30") int pageSize) {

        PageHelper.startPage(page, pageSize);
        List<TeacherVo> res = teacherService.getTeacherVos();
        PageInfo<TeacherVo> pageInfo = new PageInfo<>(res);

        PageMessage pageMessage = new PageMessage();
        pageMessage.setTotalPage(pageInfo.getPages());
        pageMessage.setTotal(pageInfo.getTotal());
        pageMessage.setNowPage(pageInfo.getPageNum());
        pageMessage.setData(res);

        return pageMessage;
    }

    @PostMapping("/add")
    public BaseMessage add(TeacherVo teacherVo) {
        return null;
    }

    @PostMapping("/set")
    public BaseMessage set(TeacherVo teacherVo) {
        return null;
    }

}
