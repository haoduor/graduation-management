package com.haoduor.graduation.service.impl;

import cn.hutool.core.date.DateUtil;
import com.haoduor.graduation.dao.ChosenSubjectMapper;
import com.haoduor.graduation.dao.FinalSubjectMapper;
import com.haoduor.graduation.model.ChosenSubject;
import com.haoduor.graduation.model.FinalSubject;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.service.ChosenSubjectService;
import com.haoduor.graduation.service.FinalSubjectService;
import com.haoduor.graduation.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import java.util.List;

@Service
public class FinalSubjectServiceImpl implements FinalSubjectService {

    @Autowired
    private FinalSubjectMapper finalSubjectMapper;

    @Autowired
    private ChosenSubjectService chosenSubjectService;

    @Autowired
    private SubjectService subjectService;


    @Override
    public boolean chose(long studentId, long subjectId) {
        FinalSubject fs = new FinalSubject();
        fs.setStudentId(studentId);
        fs.setSubjectId(subjectId);
        fs.setFinalChosenTime(DateUtil.date());

        int res = finalSubjectMapper.insert(fs);
        if (res != 1) {
            return false;
        }

        return chosenSubjectService.deleteChosenByStuId(studentId);
    }

    @Override
    public FinalSubject getStudentFinalChosen(long studentId) {
        return null;
    }

    @Override
    public List<FinalSubject> getTeacherFinalChosen(long teacherId) {
        return null;
    }

    @Override
    public List<FinalSubject> getAllFinalChosen() {
        return null;
    }
}
