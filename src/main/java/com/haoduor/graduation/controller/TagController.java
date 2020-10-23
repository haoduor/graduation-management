package com.haoduor.graduation.controller;

import cn.hutool.core.util.StrUtil;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.service.TagService;
import com.haoduor.graduation.vo.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private SubjectService subjectService;


    @PostMapping("/add")
    public BaseMessage add(@RequestParam String tagName, @RequestParam String subjectId) {
        if (StrUtil.isEmpty(tagName)) {
            return new BaseMessage(4, "标签名不能为空");
        }

        long sid = -1;
        try {
            sid = Long.parseLong(subjectId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "id格式化错误");
        }

        Subject s = subjectService.getSubjectById(sid);

        if (s == null) {
            return new BaseMessage(3, "无效课题id");
        }

        if (tagService.addTagLinkToSubject(tagName, sid)) {
            return new BaseMessage(1, "插入成功");
        }

        return new BaseMessage(5, "数据库错误");
    }

    @PostMapping("/delete")
    public BaseMessage delete(@RequestParam String tagId, @RequestParam String subjectId) {
        long _tagId  = -1;
        long _subjectId = -1;
        try {
            _tagId = Long.parseLong(tagId);
            _subjectId = Long.parseLong(subjectId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        boolean res = tagService.deleteTagLinkToSubject(_tagId, _subjectId);

        if (res) {
            return new BaseMessage(1, "删除成功");
        }

        return new BaseMessage(3, "数据库错误");
    }
}
