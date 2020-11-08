package com.haoduor.graduation.controller;

import cn.hutool.core.util.StrUtil;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.model.Tag;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.service.TagService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.DataMessage;
import com.haoduor.graduation.vo.PageMessage;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private SubjectService subjectService;

    /**
     * 根据课题获取课题中的标签
     * @param subjectId
     * @return
     */
    @GetMapping("/list")
    public DataMessage list(@RequestParam String subjectId) {
        long _subjectId = -1;

        try {
            _subjectId = Long.parseLong(subjectId);
        } catch (NumberFormatException e) {
            return new DataMessage(2, "格式化错误");
        }

        List<Tag> tags = tagService.getTagsBySubjectId(_subjectId);

        DataMessage dm = new DataMessage(1, "获取成功");
        dm.setData(tags);
        return dm;
    }

    /**
     * 添加标签
     * @param tagName
     * @param subjectId
     * @return
     */
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

    /**
     * 删除标签
     * @param tagId
     * @param subjectId
     * @return
     */
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
