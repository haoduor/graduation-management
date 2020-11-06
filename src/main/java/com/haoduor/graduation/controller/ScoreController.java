package com.haoduor.graduation.controller;

import com.haoduor.graduation.model.FinalSubject;
import com.haoduor.graduation.service.FinalSubjectService;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.BaseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private FinalSubjectService finalSubjectService;

    @Autowired
    private UserUtil userUtil;

    @PostMapping("/set")
    @RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
    public BaseMessage set(@RequestParam String subjectId,
                           @RequestParam String studentId,
                           @RequestParam int score) {
        Subject currentUser = SecurityUtils.getSubject();

        Long _subjectId;
        Long _studentId;

        try {
            _subjectId = Long.parseLong(subjectId);
            _studentId = Long.parseLong(studentId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        if (currentUser.hasRole("teacher")) {
            userUtil.cacheId(currentUser);
            Long teacherId = (Long) currentUser.getSession().getAttribute("id");
            if (!subjectService.teacherHasSubject(teacherId, _subjectId)) {
                return new BaseMessage(3, "只能评分自己的选题");
            }
        }

        if (score < 0) {
            return new BaseMessage(4, "分数不能为负数");
        }

        if (score > 100) {
            return new BaseMessage(5, "分数不能大于100");
        }

        if (finalSubjectService.setFinalSubjectScoreByStuId(_studentId, _subjectId, score)) {
            return new BaseMessage(1, "设置分数成功");
        } else {
            return new BaseMessage(6, "数据库错误");
        }
    }
}