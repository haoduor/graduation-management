package com.haoduor.graduation.service;

import com.haoduor.graduation.model.ChosenSubject;

import java.util.List;

public interface ChosenSubjectService {
    public List<ChosenSubject> getAllChosen();
    public List<ChosenSubject> getStudentChosen(long studentId);
    public List<ChosenSubject> getChosenByIds(List<Long> subjectIdList);
    public boolean deleteChosenById(long studentId, long subjectId);
    boolean deleteChosenByStuId(long studentId);
}
