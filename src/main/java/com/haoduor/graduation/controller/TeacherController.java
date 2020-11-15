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
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiresRoles("admin")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonAdapter commonAdapter;

    @Resource(name = "teacherFilter")
    private BitMapBloomFilter teacherFilter;

    /**
     * 获取教师列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public PageMessage getTeacher(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "30") int pageSize) {
        // 开始分页
        PageHelper.startPage(page, pageSize);
        // 从数据库获取全部教师
        List<TeacherVo> res = teacherService.getTeacherVos();
        // 封装分页
        PageInfo<TeacherVo> pageInfo = new PageInfo<>(res);
        // 封装返回消息
        PageMessage pageMessage = PageMessage.instance(pageInfo);
        pageMessage.setData(res);

        return pageMessage;
    }

    /**
     * 删除教师
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public BaseMessage delete(@RequestParam String id) {
        long _id = -1;

        try {
            // 格式化id
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(3, "格式化错误");
        }

        // 删除数据库中id
        boolean res = teacherService.deleteTeacherById(_id);
        if (_id != -1 && res) {
            return new BaseMessage(1, "删除成功");
        } else {
            return new BaseMessage(2, "数据库出错");
        }
    }

    /**
     * 添加教师
     * @param teacherVo
     * @return
     */
    @PostMapping("/add")
    public BaseMessage add(@RequestBody TeacherVo teacherVo) {
        // 判断工号是否已经存在
        if (teacherFilter.contains(teacherVo.getTeacherId())) {
            return new BaseMessage(2, "工号已存在");
        }

        // 转换封装类型
        TeacherDto dto = TeacherAdapter.teacherVoToDto(teacherVo);
        // 添加如数据库
        boolean res = userService.addTeacherDto(dto);

        if (res) {
            return new BaseMessage(1, "添加成功");
        } else {
            return new BaseMessage(2, "数据库错误");
        }
    }

    /**
     * 更改教师信息
     * @param teacherVo
     * @return
     */
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
