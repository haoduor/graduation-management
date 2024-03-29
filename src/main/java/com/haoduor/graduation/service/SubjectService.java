package com.haoduor.graduation.service;

import com.haoduor.graduation.dto.SubjectDto;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.vo.SubjectForm;
import com.haoduor.graduation.vo.SubjectVo;

import java.util.List;

public interface SubjectService {
    public boolean addSubject(SubjectDto subjectDto);
    public List<Subject> getSubject();
    public List<Subject> getTeacherSubject(long teacherId);
    public Subject getSubjectById(long id);
    public boolean setSubjectById(long id, SubjectDto subjectDto);
    public boolean deleteSubjectById(long id);
    public boolean hasSubject(long id);
    public boolean teacherHasSubject(long teacherId, long subjectId);
    public int countStudentChoseSubject(long id);
    public boolean choseSubject(long subjectId, long studentId);
}
