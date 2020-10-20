package com.haoduor.graduation.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.service.TeacherService;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ResponseBody
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

}
