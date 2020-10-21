package com.haoduor.graduation.model;

import java.io.Serializable;
import java.util.Date;

public class ChosenSubject implements Serializable {
    private Long studentId;

    private Long subjectId;

    private Date chosenTime;

    private static final long serialVersionUID = 737937190122310771L;

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

    public Date getChosenTime() {
        return chosenTime;
    }

    public void setChosenTime(Date chosenTime) {
        this.chosenTime = chosenTime;
    }
}