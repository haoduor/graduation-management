package com.haoduor.graduation.model;

import java.io.Serializable;
import java.util.Date;

public class FinalSubject implements Serializable {
    private Long studentId;

    private Long subjectId;

    private Date finalChosenTime;

    private static final long serialVersionUID = -5154338290957356527L;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Date getFinalChosenTime() {
        return finalChosenTime;
    }

    public void setFinalChosenTime(Date finalChosenTime) {
        this.finalChosenTime = finalChosenTime;
    }
}