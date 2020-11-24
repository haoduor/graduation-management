package com.haoduor.graduation.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.model.*;
import com.haoduor.graduation.service.*;
import com.haoduor.graduation.util.ConvertUtil;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.ChosenSubjectVo;
import com.haoduor.graduation.vo.DataMessage;
import com.haoduor.graduation.vo.PageMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // 获取学生自己选中的课题 不能超过3个
    @GetMapping("/student")
    @RequiresRoles(value = {"student", "admin"}, logical = Logical.OR)
    public DataMessage studentChosen(@RequestParam String studentId) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        long _id;

        // 格式化传入id
        try {
            _id = Long.parseLong(studentId);
        } catch (NumberFormatException e) {
            return new DataMessage(2, "格式化错误");
        }

        // 判断是否为学生 并且 传入id与用户session 相同
        if (!userUtil.isMe(_id, currentUser) && !currentUser.hasRole("admin")) {
            return new DataMessage(3, "只能获取自己选择的课题");
        }

        // 获取学生选择的课题 列表内可能为空
        List<ChosenSubject> chosenSubjectList = chosenSubjectService.getStudentChosen(_id);
        // 转换实体类
        List<ChosenSubjectVo> data = convertData(chosenSubjectList);

        // 设置返回信息
        DataMessage dm = new DataMessage(1, "获取成功");
        dm.setData(data);

        return dm;
    }

    // 教师获取选择了自己课题的学生
    @GetMapping("/teacher")
    @RequiresRoles(value = {"teacher", "admin"}, logical = Logical.OR)
    public PageMessage teacherChosen(@RequestParam String teacherId,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "30") int pageSize) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        long _id;
        // 格式化传入id
        try {
            _id = Long.parseLong(teacherId);
        } catch (NumberFormatException e) {
            return new PageMessage(2, "格式化错误");
        }

        // 判断是否为教师 并且 传入id与用户session 相同
        if (!userUtil.isMe(_id, currentUser) && !currentUser.hasRole("admin")) {
            return new PageMessage(3, "只能获取自己的课题");
        }

        // 获取教师的课题
        List<Subject> subjects = subjectService.getTeacherSubject(_id);

        // 映射课题id 对 课题实例
        Map<Long, Subject> subjectMap = subjects.stream()
                                                .collect(Collectors.toMap(Subject::getId, e -> e));

        // 转变课题实例列表 为 课题id列表
        List<Long> subjectIds = subjects.stream()
                                        .map(Subject::getId)
                                        .collect(Collectors.toList());

        PageHelper.startPage(page, pageSize);
        // 根据课题id 查询被选中的课题
        List<ChosenSubject> chosenSubjectList = chosenSubjectService.getChosenByIds(subjectIds);
        PageInfo<ChosenSubject> pages = new PageInfo<>(chosenSubjectList);
        PageHelper.clearPage();

        PageMessage pm = PageMessage.instance(pages);
        // 转换封装实体类
        pm.setData(convertData(chosenSubjectList, subjectMap));

        return pm;
    }

    // 管理员获取所有被选择的课题
    @GetMapping("/all")
    @RequiresRoles("admin")
    public PageMessage allChosen(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "30") int pageSize) {
        PageHelper.startPage(page, pageSize);
        // 获取所有被选中的课题
        List<ChosenSubject> chosenSubjectList = chosenSubjectService.getAllChosen();
        PageInfo<ChosenSubject> pages = new PageInfo<>(chosenSubjectList);
        PageHelper.clearPage();

        PageMessage pm = PageMessage.instance(pages);
        // 转换封装实体类
        pm.setData(convertData(chosenSubjectList));

        return pm;
    }

    /**
     * 数据转换方法
     * @param chosenSubjectList 选择课题列表
     * @return
     */
    private List<ChosenSubjectVo> convertData(List<ChosenSubject> chosenSubjectList) {
        List<ChosenSubjectVo> res = new LinkedList<>();

        for (ChosenSubject cs: chosenSubjectList) {
            long subjectId = cs.getSubjectId();

            // 根据被选中的课题id 获取课题信息
            Subject s = subjectService.getSubjectById(subjectId);

            ChosenSubjectVo vo = toChosenSubjectVo(cs, s);

            res.add(vo);
        }

        return res;
    }

    /**
     * 数据转换方法
     * @param chosenSubjectList 选择课题列表
     * @param subjectMap 课题信息map
     * @return
     */
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
