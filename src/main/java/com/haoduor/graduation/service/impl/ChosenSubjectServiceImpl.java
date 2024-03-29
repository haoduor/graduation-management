package com.haoduor.graduation.service.impl;

import com.haoduor.graduation.dao.ChosenSubjectMapper;
import com.haoduor.graduation.model.ChosenSubject;
import com.haoduor.graduation.model.ChosenSubjectExample;
import com.haoduor.graduation.service.ChosenSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChosenSubjectServiceImpl implements ChosenSubjectService {

    @Autowired
    private ChosenSubjectMapper chosenSubjectMapper;

    private ChosenSubjectExample instance() {
        return new ChosenSubjectExample();
    }

    @Override
    public List<ChosenSubject> getAllChosen() {
        ChosenSubjectExample cse = instance();

        return chosenSubjectMapper.selectByExample(cse);
    }

    @Override
    public List<ChosenSubject> getStudentChosen(long studentId) {
        ChosenSubjectExample cse = instance();
        cse.createCriteria().andStudentIdEqualTo(studentId);
        return chosenSubjectMapper.selectByExample(cse);
    }

    @Override
    public List<ChosenSubject> getChosenByIds(List<Long> subjectIdList) {
        ChosenSubjectExample cse = instance();
        cse.createCriteria().andSubjectIdIn(subjectIdList);
        return chosenSubjectMapper.selectByExample(cse);
    }

    @Override
    public boolean deleteChosenById(long studentId, long subjectId) {
        return false;
    }

    @Override
    public boolean deleteChosenByStuId(long studentId) {
        ChosenSubjectExample cse = instance();
        cse.createCriteria().andStudentIdEqualTo(studentId);

        int res = chosenSubjectMapper.deleteByExample(cse);

        return res == 1;
    }

}
