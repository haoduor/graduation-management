package com.haoduor.graduation.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

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

    // 删除学生
    @PostMapping("/delete")
    public BaseMessage delete(long id) {
        if (studentService.deleteStudentById(id)) {
            return new BaseMessage(1, "用户删除成功");
        } else {
            return new BaseMessage(2, "用户删除失败");
        }
    }

    // 更改学生
    @PostMapping("/set")
    public BaseMessage set() {
        return null;
    }
}
