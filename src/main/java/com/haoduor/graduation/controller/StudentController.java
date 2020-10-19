package com.haoduor.graduation.controller;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.adapter.StudentAdapter;
import com.haoduor.graduation.dto.StudentDto;
import com.haoduor.graduation.model.Student;
import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Resource(name = "studentFilter")
    private BitMapBloomFilter studentFilter;

    /**
     * 获取用户列表
     * @param page 第几页
     * @param pageSize 页面数据存在多少
     * @return
     */
    @GetMapping("/list")
    public PageMessage getStudent(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "30") int pageSize) {

        PageHelper.startPage(page, pageSize);
        List<StudentVo> res = studentService.getStudentVos();
        PageInfo<StudentVo> pages = new PageInfo<>(res);

        PageMessage pageMessage = new PageMessage();
        pageMessage.setTotalPage(pages.getPages());
        pageMessage.setTotal(pages.getTotal());
        pageMessage.setNowPage(pages.getPageNum());
        pageMessage.setData(res);

        return pageMessage;
    }

    // 删除学生
    @PostMapping("/delete")
    public BaseMessage delete(@RequestParam long id) {
        if (studentService.deleteStudentById(id)) {
            return new BaseMessage(1, "用户删除成功");
        } else {
            return new BaseMessage(2, "用户删除失败");
        }
    }

    // 更改学生 (无法更改学号)
    @PostMapping("/set")
    public BaseMessage set(@Valid @RequestParam StudentVo vo) {
        boolean res = studentService.updateStudentByVo(vo);
        if (res) {
            return new BaseMessage(1, "更新成功");
        }

        return new BaseMessage(2, "更新用户数据失败");
    }

    /**
     * 添加学生 但是不能设置密码 (需要更改) 代码占时删除
     * @param vo 可以不设置id
     * @return
     */
    @PostMapping("/add")
    public BaseMessage add(@RequestParam StudentVo vo) {
        if (vo != null) {
            StudentDto tmp = null;
            try {
                tmp = StudentAdapter.studentVoToDto(vo);
            } catch (NumberFormatException e) {
                return new BaseMessage(2, "格式化错误");
            }

            boolean res = userService.addStudentDto(tmp);

            if (res) {
                return new BaseMessage(1, "添加成功");
            } else {
                return new BaseMessage(4, "数据库错误");
            }
        } else {
            return new BaseMessage(3, "字段不能为空");
        }
    }
}
