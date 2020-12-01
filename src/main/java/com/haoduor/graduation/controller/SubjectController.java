package com.haoduor.graduation.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.adapter.SubjectAdapter;
import com.haoduor.graduation.annotation.TimeCount;
import com.haoduor.graduation.annotation.TimeLimit;
import com.haoduor.graduation.dto.SubjectDto;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.model.Tag;
import com.haoduor.graduation.model.Teacher;
import com.haoduor.graduation.service.*;
import com.haoduor.graduation.util.ConvertUtil;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.SubjectForm;
import com.haoduor.graduation.vo.SubjectVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FinalSubjectService finalSubjectService;

    @Autowired
    private UserUtil userUtil;

    // 修改课题接口
    @PostMapping("/set")
    @RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
    public BaseMessage set(@RequestBody SubjectForm subjectForm, @RequestParam String id) {
        SubjectDto dto = null;
        long _subjectId;

        try {
            dto = SubjectAdapter.SubjectFormToDto(subjectForm);
            _subjectId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        boolean res = subjectService.setSubjectById(_subjectId, dto);

        if (res) {
            return new BaseMessage(1, "新增成功");
        } else {
            return new BaseMessage(3, "数据库出错");
        }
    }

    // 管理员添加课题接口
    @PostMapping("/add")
    @RequiresRoles("admin")
    public BaseMessage add(@RequestBody SubjectForm subjectForm) {
        SubjectDto dto = null;

        try {
            dto = SubjectAdapter.SubjectFormToDto(subjectForm);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        if (subjectService.addSubject(dto)) {
            return new BaseMessage(1, "新增成功");
        } else {
            return new BaseMessage(3, "数据库出错");
        }
    }

    // 教师添加课题接口
    @PostMapping("/teacher/add")
    @RequiresRoles("teacher")
    public BaseMessage teacherAdd(@RequestBody SubjectForm subjectForm) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

        SubjectDto dto = null;

        try {
            dto = SubjectAdapter.SubjectFormToDto(subjectForm);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        Long _id = dto.getTeacherId();
        if (!userUtil.isMe(_id, currentUser)) {
            return new BaseMessage(3, "无法为其他教师添加课题");
        }

        if (subjectService.addSubject(dto)) {
            return new BaseMessage(1, "新增成功");
        } else {
            return new BaseMessage(3, "数据库出错");
        }
    }

    // 获取课题列表
    @TimeCount
    @GetMapping("/list")
    public PageMessage list(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "30") int pageSize,
                            @RequestParam(required = false) String teacherId) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        List<Subject> subs;

        if (!StrUtil.isEmpty(teacherId)) {
            Long id;
            try {
                id = Long.parseLong(teacherId);
            } catch (NumberFormatException e) {
                return new PageMessage(2, "教师id格式化错误");
            }
            // 根据教师id 获取选题
            subs = subjectService.getTeacherSubject(id);
        } else {
            // 获取所有选题
            subs = subjectService.getSubject();
        }

        PageInfo<Subject> pages = new PageInfo<>(subs);
        // 关闭分页
        PageHelper.clearPage();

        // 封装分页 信息
        PageMessage pm = PageMessage.instance(pages);

        // 连接课题 与 课题的标签

        List<SubjectVo> vos = new LinkedList<>();
        for (Subject s: subs) {
            // 根据课题查询课题拥有的标签
            List<Tag> tags = tagService.getTagsBySubjectId(s.getId());
            // 装换为页面封装类
            SubjectVo vo = ConvertUtil.convert(SubjectVo.class, s);
            // 根据课题查询创建的教师
            Teacher t = teacherService.getTeacherById(s.getTeacherid());

            if (t != null) {
                vo.setTeacherName(t.getName());
            }

            // 设置数据
            vo.setTags(tags);
            vos.add(vo);
        }

        pm.setData(vos);
        return pm;
    }

    /**
     * 学生选择课题接口
     * @param subjectId 课题id
     * @param studentId 学生的id
     * @return
     */
    @PostMapping("/chose")
    @RequiresRoles("student")
    public BaseMessage choseSubject(@RequestParam String subjectId, @RequestParam String studentId) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

        long _subjectId;
        long _studentId;
        try {
            _subjectId = Long.parseLong(subjectId);
            _studentId = Long.parseLong(studentId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        if (!userUtil.isMe(_studentId, currentUser)) {
            return new BaseMessage(10, "只能选择自己");
        }

        if (subjectService.hasSubject(_subjectId)) {
            return new BaseMessage(3, "课题不存在");
        }

        if (studentService.hasStudent(_studentId)) {
            return new BaseMessage(4, "学生不存在");
        }

        if (finalSubjectService.hasFinalSubject(_studentId)) {
            return new BaseMessage(9, "该学生已有最终选题");
        }

        int count = subjectService.countStudentChoseSubject(_studentId);
        if (count == 3) {
            return new BaseMessage(7, "选题不能超过3个");
        }

        boolean res = subjectService.choseSubject(_subjectId, _studentId);
        if (res) {
            return new BaseMessage(1, "选择成功");
        } else {
            return new BaseMessage(8, "数据库出错");
        }
    }

    /**
     * 删除课题接口
     * @param teacherId 教师id
     * @param subjectId 课题id
     * @return
     */
    @PostMapping("/delete")
    @RequiresRoles(value = {"teacher", "admin"}, logical = Logical.OR)
    public BaseMessage delete(@RequestParam String teacherId,
                              @RequestParam String subjectId) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

        Long _subjectId;
        Long _teacherId;
        try {
            _subjectId = Long.parseLong(subjectId);
            _teacherId = Long.parseLong(teacherId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        if (currentUser.hasRole("teacher") && !userUtil.isMe(_teacherId, currentUser)) {
            return new BaseMessage(3, "只能修改自己的课题");
        }

        if (!subjectService.teacherHasSubject(_teacherId, _subjectId)) {
            return new BaseMessage(4, "教师不拥有该课题");
        }

        if (subjectService.deleteSubjectById(_subjectId)) {
            return new BaseMessage(1, "删除成功");
        } else {
            return new BaseMessage(5, "数据库错误");
        }
    }
}
