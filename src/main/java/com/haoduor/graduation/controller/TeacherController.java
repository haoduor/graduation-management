package com.haoduor.graduation.controller;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.adapter.CommonAdapter;
import com.haoduor.graduation.adapter.TeacherAdapter;
import com.haoduor.graduation.dto.TeacherDto;
import com.haoduor.graduation.service.TeacherService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonAdapter commonAdapter;

    @Resource(name = "teacherFilter")
    private BitMapBloomFilter teacherFilter;

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

    @PostMapping("/delete")
    public BaseMessage delete(@RequestParam String id) {
        long _id = -1;

        try {
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(3, "格式化错误");
        }

        boolean res = teacherService.deleteTeacherById(_id);
        if (_id != -1 && res) {
            return new BaseMessage(1, "删除成功");
        } else {
            return new BaseMessage(2, "数据库出错");
        }
    }

    @PostMapping("/add")
    public BaseMessage add(@RequestBody TeacherVo teacherVo) {
        if (teacherFilter.contains(teacherVo.getTeacherId())) {
            return new BaseMessage(2, "工号已存在");
        }

        TeacherDto dto = TeacherAdapter.teacherVoToDto(teacherVo);
        boolean res = userService.addTeacherDto(dto);

        if (res) {
            return new BaseMessage(1, "添加成功");
        } else {
            return new BaseMessage(2, "数据库错误");
        }
    }

    @PostMapping("/set")
    public BaseMessage set(@RequestParam TeacherVo teacherVo) {
        boolean res = teacherService.updateTeacherByVo(teacherVo);

        if (res) {
            return new BaseMessage(1, "更新成功");
        } else {
            return new BaseMessage(2, "数据库错误");
        }
    }

}
