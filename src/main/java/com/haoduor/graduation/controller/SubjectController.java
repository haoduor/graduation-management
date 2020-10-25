package com.haoduor.graduation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.adapter.SubjectAdapter;
import com.haoduor.graduation.dto.SubjectDto;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.model.Tag;
import com.haoduor.graduation.model.Teacher;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.service.TagService;
import com.haoduor.graduation.service.TeacherService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import com.haoduor.graduation.vo.SubjectForm;
import com.haoduor.graduation.vo.SubjectVo;
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

    @PostMapping("/set")
    public BaseMessage set(@RequestBody SubjectForm subjectForm) {
        SubjectDto dto = null;

        try {
            dto = SubjectAdapter.SubjectFormToDto(subjectForm);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        boolean res = subjectService.setSubjectById(1L, dto);

        if (res) {
            return new BaseMessage(1, "新增成功");
        } else {
            return new BaseMessage(3, "数据库出错");
        }
    }

    @PostMapping("/add")
    public BaseMessage add(@RequestBody SubjectForm subjectForm) {
        SubjectDto dto = null;

        try {
            dto = SubjectAdapter.SubjectFormToDto(subjectForm);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        boolean res = subjectService.addSubject(dto);

        if (res) {
            return new BaseMessage(1, "新增成功");
        } else {
            return new BaseMessage(3, "数据库出错");
        }
    }

    @GetMapping("/list")
    public PageMessage list(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "30") int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Subject> subs = subjectService.getSubject();
        PageInfo<Subject> pages = new PageInfo<>(subs);
        PageHelper.clearPage();

        PageMessage pm = new PageMessage();
        pm.setTotal(pages.getTotal());
        pm.setNowPage(pages.getPageNum());
        pm.setTotalPage(pages.getPages());

        List<SubjectVo> vos = new LinkedList<>();
        for (Subject s: subs) {
            List<Tag> tags = tagService.getTagsBySubjectId(s.getId());

            String json = JSONObject.toJSONString(s);
            SubjectVo vo = JSON.parseObject(json, SubjectVo.class);

            Teacher t = teacherService.getTeacherById(s.getTeacherid());

            if (t != null) {
                vo.setTeacherName(t.getName());
            }

            vo.setTags(tags);
            vos.add(vo);
        }

        pm.setData(vos);
        return pm;
    }

}
