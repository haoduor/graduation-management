package com.haoduor.graduation.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.adapter.SubjectAdapter;
import com.haoduor.graduation.dao.ChosenSubjectMapper;
import com.haoduor.graduation.dao.SubjectMapper;
import com.haoduor.graduation.dao.SubjectToTagMapper;
import com.haoduor.graduation.dto.SubjectDto;
import com.haoduor.graduation.model.*;
import com.haoduor.graduation.service.SubjectService;
import com.haoduor.graduation.service.TagService;
import com.haoduor.graduation.util.ConvertUtil;
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
    private ChosenSubjectMapper chosenSubjectMapper;

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

    @Override
    public List<Subject> getTeacherSubject(long teacherId) {
        SubjectExample se = new SubjectExample();
        se.createCriteria().andTeacheridEqualTo(teacherId);

        return subjectMapper.selectByExample(se);
    }

    @Override
    public Subject getSubjectById(long id) {
        SubjectExample se = new SubjectExample();
        se.createCriteria().andIdEqualTo(id);

        List<Subject> list = subjectMapper.selectByExample(se);

        if (list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public boolean setSubjectById(long id, SubjectDto subjectDto) {
        Subject s = ConvertUtil.convert(Subject.class, subjectDto);
        s.setId(id);
        s.setTeacherid(subjectDto.getTeacherId());

        SubjectExample se = new SubjectExample();
        se.createCriteria().andIdEqualTo(id);

        int res = subjectMapper.updateByExampleSelective(s, se);

        return res == 1;
    }

    @Override
    public boolean deleteSubjectById(long id) {
        SubjectExample se = new SubjectExample();
        se.createCriteria().andIdEqualTo(id);

        int res = subjectMapper.deleteByExample(se);

        return res == 1;
    }

    @Override
    public boolean hasSubject(long id) {
        return getSubjectById(id) == null;
    }

    @Override
    public boolean teacherHasSubject(long teacherId, long subjectId) {
        SubjectExample se = new SubjectExample();
        se.createCriteria()
          .andTeacheridEqualTo(teacherId)
          .andIdEqualTo(subjectId);

        return subjectMapper.countByExample(se) == 1;
    }

    @Override
    public int countStudentChoseSubject(long id) {
        ChosenSubjectExample cse = new ChosenSubjectExample();
        cse.createCriteria().andStudentIdEqualTo(id);

        return chosenSubjectMapper.countByExample(cse);
    }

    @Override
    public boolean choseSubject(long subjectId, long studentId) {
        ChosenSubject cs = new ChosenSubject();
        cs.setChosenTime(DateUtil.date());
        cs.setStudentId(studentId);
        cs.setSubjectId(subjectId);

        int res = chosenSubjectMapper.insert(cs);

        return res == 1;
    }

}
