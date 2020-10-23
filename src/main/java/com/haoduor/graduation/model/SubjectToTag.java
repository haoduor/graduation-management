package com.haoduor.graduation.model;

import java.io.Serializable;

public class SubjectToTag implements Serializable {
    private Long subjectId;

    private Long tagId;

    private static final long serialVersionUID = 1890633001689685755L;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}