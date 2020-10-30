package com.haoduor.graduation.controller;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.model.*;
import com.haoduor.graduation.service.FinalSubjectService;
import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.service.TeacherService;
import com.haoduor.graduation.util.ConvertUtil;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/final")
public class FinalSubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private FinalSubjectService finalSubjectService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserUtil userUtil;

    @RequestMapping("/chose")
    @RequiresRoles(value = {"teacher", "admin"}, logical = Logical.OR)
    public BaseMessage chose(@RequestParam String studentId,
                             @RequestParam String subjectId) {
        long _studentId;
        long _subjectId;

        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        userUtil.cacheId(currentUser);

        Session se = currentUser.getSession();
        Long id = (Long) se.getAttribute("id");

        try {
            _studentId = Long.parseLong(studentId);
            _subjectId = Long.parseLong(subjectId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化失败");
        }

        if (!subjectService.teacherHasSubject(id, _subjectId) && !currentUser.hasRole("admin")) {
            return new BaseMessage(3, "不能选择非自己的选题");
        }

        if (finalSubjectService.chose(_studentId, _subjectId)) {
            return new BaseMessage(1, "选择成功");
        } else {
            return new BaseMessage(4, "数据库错误");
        }
    }

    @RequestMapping("/student")
    @RequiresRoles(value = {"student", "admin"}, logical = Logical.OR)
    public DataMessage studentFinal(@RequestParam String studentId) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

        Long id;
        try {
            id = Long.parseLong(studentId);
        } catch (NumberFormatException e) {
            return new DataMessage(2, "格式化失败");
        }

        if (!userUtil.isMe(id, currentUser) && !currentUser.hasRole("admin")) {
            return new DataMessage(3, "不能查询其他用户的最终选题");
        }

        FinalSubject fs = finalSubjectService.getStudentFinalChosen(id);
        if (fs !=  null) {
            DataMessage dm = new DataMessage(1, "获取成功");
            FinalSubjectVo finalSubjectVo = convertData(fs);
            dm.setData(finalSubjectVo);
            return dm;
        } else {
            return new DataMessage(4, "该学生没有最终选题");
        }
    }

    @RequestMapping("/teacher")
    @RequiresRoles(value = {"teacher", "admin"}, logical = Logical.OR)
    public PageMessage teacherFinal(@RequestParam String teacherId,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "30") int pageSize) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

        Long id;
        try {
            id = Long.parseLong(teacherId);
        } catch (NumberFormatException e) {
            return new PageMessage(2, "格式化失败");
        }

        if (!userUtil.isMe(id, currentUser) && !currentUser.hasRole("admin")) {
            return new PageMessage(3, "不能查询其他用户的最终选题");
        }

        PageHelper.startPage(page, pageSize);
        List<FinalSubject> finalSubjectList = finalSubjectService.getTeacherFinalChosen(id);
        PageInfo<FinalSubject> pages = new PageInfo<>(finalSubjectList);
        PageHelper.clearPage();

        PageMessage pm = PageMessage.instance(pages);
        pm.setData(convertData(finalSubjectList));
        return pm;
    }


    @RequestMapping("/all")
    @RequiresRoles("admin")
    public PageMessage all(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "30") int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<FinalSubject> finalSubjectList = finalSubjectService.getAllFinalChosen();
        PageInfo<FinalSubject> pages = new PageInfo<>(finalSubjectList);
        PageHelper.clearPage();

        PageMessage pm = PageMessage.instance(pages);
        pm.setData(convertData(finalSubjectList));
        return pm;
    }

    private List<FinalSubjectVo> convertData(List<FinalSubject> finalSubjectList) {
        List<FinalSubjectVo> res = new LinkedList<>();

        for (FinalSubject fs: finalSubjectList) {
            res.add(convertData(fs));
        }

        return res;
    }

    private FinalSubjectVo convertData(FinalSubject fs) {
        long subjectId = fs.getSubjectId();

        Subject s = subjectService.getSubjectById(subjectId);

        if (s == null) {
            return null;
        }

        return toFinalSubjectVo(fs, s);
    }

    private FinalSubjectVo toFinalSubjectVo(FinalSubject fs, Subject subject) {
        SubjectVo subjectVo = toSubjectVo(subject);
        FinalSubjectVo finalSubjectVo = ConvertUtil.convert(FinalSubjectVo.class, subjectVo);

        Student stu = studentService.getStudentById(fs.getStudentId());
        if (stu != null) {
            finalSubjectVo.setStudentId(stu.getUserId());
            finalSubjectVo.setStudentName(stu.getName());
        }

        finalSubjectVo.setChosenTime(fs.getFinalChosenTime());

        return finalSubjectVo;
    }

    private SubjectVo toSubjectVo(Subject subject) {
        SubjectVo subjectVo = ConvertUtil.convert(SubjectVo.class, subject);

        long teacherId = subject.getTeacherid();
        Teacher t = teacherService.getTeacherById(teacherId);

        if (t != null) {
            subjectVo.setTeacherName(t.getName());
            subjectVo.setTeacherId(t.getUserId());
        }

        return subjectVo;
    }
}
