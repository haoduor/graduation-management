package com.haoduor.graduation.controller;

import com.haoduor.graduation.model.FinalSubject;
import com.haoduor.graduation.service.FinalSubjectService;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.BaseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/final")
public class FinalSubjectController {


    @Autowired
    private SubjectService subjectService;

    @Autowired
    private FinalSubjectService finalSubjectService;

    @Autowired
    private UserUtil userUtil;

    @RequestMapping("/chose")
    @RequiresRoles("teacher")
    public BaseMessage chose(@RequestParam String studentId,
                             @RequestParam String subjectId) {
        long _studentId;
        long _subjectId;

        Subject currentUser = SecurityUtils.getSubject();
        userUtil.cacheId(currentUser);

        Session se = currentUser.getSession();
        Long id = (Long) se.getAttribute("id");

        try {
            _studentId = Long.parseLong(studentId);
            _subjectId = Long.parseLong(subjectId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化失败");
        }

        if (!subjectService.teacherHasSubject(id, _subjectId)) {
            return new BaseMessage(3, "不能选择非自己的选题");
        }

        if (finalSubjectService.chose(_studentId, _subjectId)) {
            return new BaseMessage(1, "选择成功");
        } else {
            return new BaseMessage(4, "数据库错误");
        }
    }
}
