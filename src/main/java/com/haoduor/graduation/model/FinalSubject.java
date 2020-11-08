package com.haoduor.graduation.model;

import java.io.Serializable;
import java.util.Date;

public class FinalSubject implements Serializable {
    private Long studentId;

    private Long subjectId;

    private Date finalChosenTime;

    private Integer score;

    private static final long serialVersionUID = 600072399828546295L;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}