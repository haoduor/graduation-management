package com.haoduor.graduation.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.adapter.SubjectAdapter;
import com.haoduor.graduation.dao.SubjectMapper;
import com.haoduor.graduation.dao.SubjectToTagMapper;
import com.haoduor.graduation.dto.SubjectDto;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.model.SubjectExample;
import com.haoduor.graduation.model.SubjectToTag;
import com.haoduor.graduation.model.Tag;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.service.TagService;
import com.haoduor.graduation.vo.SubjectForm;
import com.haoduor.graduation.vo.SubjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectToTagMapper subjectToTagMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private Snowflake snowflake;

    @Override
    public boolean addSubject(SubjectDto subjectDto) {
        Subject s = new Subject();
        s.setId(snowflake.nextId());
        s.setTitle(subjectDto.getTitle());
        s.setContent(subjectDto.getContent());
        s.setDifficult(subjectDto.getDifficult());
        s.setCreateTime(DateUtil.date());
        s.setSource(subjectDto.getSource());
        s.setTeacherid(subjectDto.getTeacherId());

        int res = subjectMapper.insert(s);

        if (res != 1) {
            return false;
        }

        List<Tag> tags = new LinkedList<>();
        for (String t: subjectDto.getTags()) {
            tags.add(tagService.getOrAddTagByName(t));
        }
        List<SubjectToTag> stt = SubjectAdapter.tagToSubjecttoTag(tags, s.getId());

        for (SubjectToTag st : stt) {
            int t = subjectToTagMapper.insert(st);

            if (t != 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Subject> getSubject() {
        SubjectExample se = new SubjectExample();

        return subjectMapper.selectByExample(se);
    }


}
