package com.haoduor.graduation.service;

import com.haoduor.graduation.model.FinalSubject;

import java.util.List;

public interface FinalSubjectService {
    public boolean chose(long studentId, long subjectId);
    public FinalSubject getStudentFinalChosen(long studentId);
    public List<FinalSubject> getTeacherFinalChosen(long teacherId);
    public List<FinalSubject> getAllFinalChosen();
    public boolean hasFinalSubject(long studentId);
    public boolean setFinalSubjectScoreByStuId(Long studentId, Long subjectId, int score);
}
