package com.haoduor.graduation.controller;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.model.*;
import com.haoduor.graduation.service.*;
import com.haoduor.graduation.util.ConvertUtil;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.ChosenSubjectVo;
import com.haoduor.graduation.vo.DataMessage;
import com.haoduor.graduation.vo.PageMessage;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.CSS;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chosen")
public class ChosenSubjectController {

    @Autowired
    private ChosenSubjectService chosenSubjectService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserUtil userUtil;

    @GetMapping("/student")
    @RequiresRoles(value = {"student", "admin"}, logical = Logical.OR)
    public DataMessage studentChosen(@RequestParam String studentId) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        long _id;

        try {
            _id = Long.parseLong(studentId);
        } catch (NumberFormatException e) {
            return new DataMessage(2, "格式化错误");
        }

        if (!userUtil.isMe(_id, currentUser) && !currentUser.hasRole("admin")) {
            return new DataMessage(3, "只能获取自己选择的课题");
        }

        List<ChosenSubject> chosenSubjectList = chosenSubjectService.getStudentChosen(_id);
        List<ChosenSubjectVo> data = convertData(chosenSubjectList);

        DataMessage dm = new DataMessage(1, "获取成功");
        dm.setData(data);

        return dm;
    }

    @GetMapping("/teacher")
    @RequiresRoles(value = {"teacher", "admin"}, logical = Logical.OR)
    public PageMessage teacherChosen(@RequestParam String teacherId,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "30") int pageSize) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        long _id;
        try {
            _id = Long.parseLong(teacherId);
        } catch (NumberFormatException e) {
            return new PageMessage(2, "格式化错误");
        }

        if (!userUtil.isMe(_id, currentUser) && !currentUser.hasRole("admin")) {
            return new PageMessage(3, "只能获取自己的课题");
        }

        List<Subject> subjects = subjectService.getTeacherSubject(_id);

        Map<Long, Subject> subjectMap = subjects.stream()
                                                .collect(Collectors.toMap(Subject::getId, e -> e));

        List<Long> subjectIds = subjects.stream()
                                        .map(Subject::getId)
                                        .collect(Collectors.toList());

        PageHelper.startPage(page, pageSize);
        List<ChosenSubject> chosenSubjectList = chosenSubjectService.getChosenByIds(subjectIds);
        PageInfo<ChosenSubject> pages = new PageInfo<>(chosenSubjectList);
        PageHelper.clearPage();

        PageMessage pm = PageMessage.instance(pages);
        pm.setData(convertData(chosenSubjectList, subjectMap));

        return pm;
    }

    @GetMapping("/all")
    @RequiresRoles("admin")
    public PageMessage allChosen(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "30") int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ChosenSubject> chosenSubjectList = chosenSubjectService.getAllChosen();
        PageInfo<ChosenSubject> pages = new PageInfo<>(chosenSubjectList);
        PageHelper.clearPage();

        PageMessage pm = PageMessage.instance(pages);
        pm.setData(convertData(chosenSubjectList));

        return pm;
    }

    private List<ChosenSubjectVo> convertData(List<ChosenSubject> chosenSubjectList) {
        List<ChosenSubjectVo> res = new LinkedList<>();

        for (ChosenSubject cs: chosenSubjectList) {
            long subjectId = cs.getSubjectId();

            Subject s = subjectService.getSubjectById(subjectId);

            ChosenSubjectVo vo = toChosenSubjectVo(cs, s);

            res.add(vo);
        }

        return res;
    }

    private List<ChosenSubjectVo> convertData(List<ChosenSubject> chosenSubjectList,
                                              Map<Long, Subject> subjectMap) {
        List<ChosenSubjectVo> res = new LinkedList<>();
        for (ChosenSubject cs: chosenSubjectList) {
            long subjectId = cs.getSubjectId();

            Subject s = subjectMap.get(subjectId);

            ChosenSubjectVo vo = toChosenSubjectVo(cs, s);

            res.add(vo);
        }

        return res;
    }

    public ChosenSubjectVo toChosenSubjectVo(ChosenSubject cs, Subject s) {
        long subjectId = cs.getSubjectId();

        ChosenSubjectVo vo = ConvertUtil.convert(ChosenSubjectVo.class, s);

        List<Tag> tags = tagService.getTagsBySubjectId(subjectId);
        vo.setTags(tags);

        Student stu = studentService.getStudentById(cs.getStudentId());
        Teacher t = teacherService.getTeacherById(s.getTeacherid());

        if (stu != null) {
            vo.setStudentId(stu.getUserId());
            vo.setStudentName(stu.getName());
        }

        if (t != null) {
            vo.setTeacherId(t.getUserId());
            vo.setTeacherName(t.getName());
        }

        return vo;
    }
}
