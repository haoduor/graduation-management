package com.haoduor.graduation.adapter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haoduor.graduation.dto.SubjectDto;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.model.SubjectToTag;
import com.haoduor.graduation.model.Tag;
import com.haoduor.graduation.vo.SubjectForm;
import com.haoduor.graduation.vo.SubjectVo;

import java.util.LinkedList;
import java.util.List;

public class SubjectAdapter {

    private SubjectAdapter(){}

    public static SubjectDto SubjectFormToDto(SubjectForm form) throws NumberFormatException{
        String json = JSONObject.toJSONString(form);

        SubjectDto dto = JSON.parseObject(json, SubjectDto.class);

        dto.setTeacherId(Long.parseLong(form.getTeacherId()));

        return dto;
    }

    public static List<SubjectToTag> tagToSubjecttoTag(List<Tag> tags, long subjectId) {
        List<SubjectToTag> res = new LinkedList<>();
        for (Tag t: tags) {
            SubjectToTag tmp = new SubjectToTag();
            tmp.setSubjectId(subjectId);
            tmp.setTagId(t.getId());

            res.add(tmp);
        }

        return res;
    }
}
